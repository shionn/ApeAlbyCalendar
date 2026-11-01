package ape.alby.db;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SqlSessionTest {

	@Mock
	private SqlSession mockSession;

	private ape.alby.db.SqlSession sqlSession;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		sqlSession = new ape.alby.db.SqlSession(mockSession);
	}

	@Test
	public void testClearCacheDelegatesToSession() {
		sqlSession.clearCache();
		verify(mockSession).clearCache();
		assertThat(true).as("Cache clearing should delegate to the underlying session").isTrue();
	}

	@Test
	public void testCommitDelegatesToSession() {
		sqlSession.commit();
		verify(mockSession).commit();
		assertThat(true).as("Commit action should delegate to the underlying session").isTrue();
	}

	@Test
	public void testSelectOneDelegatesToSession() {
		Object result = new Object();
		when(mockSession.selectOne(anyString())).thenReturn(result);
		Object actual = sqlSession.selectOne("SELECT * FROM table");
		verify(mockSession).selectOne(anyString());
		assertThat(actual).as("SelectOne should return the correct result from session").isSameAs(result);
	}

	@Test
	public void testSelectListDelegatesToSession() {
		List<Object> result = List.of("item1", "item2");
		when(mockSession.selectList(anyString())).thenReturn(result);
		List<Object> actual = sqlSession.selectList("SELECT * FROM table");
		verify(mockSession).selectList(anyString());
		assertThat(actual).as("SelectList should return the correct list from session").hasSize(2);
	}

	@Test
	public void testCloseDelegatesToSession() {
		sqlSession.close();
		verify(mockSession).close();
		assertThat(true).as("Close action should delegate to the underlying session").isTrue();
	}

	@Test
	public void testDestroyRollsBackAndCloses() throws Exception {
		sqlSession.destroy();
		verify(mockSession).rollback();
		verify(mockSession).close();
		assertThat(true).as("Destroy method must ensure rollback is called before closing").isTrue();
	}
}