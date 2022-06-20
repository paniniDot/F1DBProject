package f1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import f1.db.ConnectionProvider;
import f1.db.tables.TabellaSponsor;
import f1.model.Sponsor;

class TabellaSponsorTest {

	final static String username = "root";
    final static String password = "mattimichi";
    final static String dbName = "f1";
    
    final static ConnectionProvider connectionProvider = new ConnectionProvider(username, password, dbName);
    final static TabellaSponsor tabSpon = new TabellaSponsor(connectionProvider.getMySQLConnection());

    final Sponsor s1 = new Sponsor("GLTT", "Gillette", "Germania");
    final Sponsor s2 = new Sponsor("PRLL", "Pirelli", "Italia");

    @BeforeEach
    void setUp() throws Exception {
    	tabSpon.dropTable();
    	tabSpon.createTable();
    }

    @AfterEach
    void tearDown() throws Exception {
    	tabSpon.dropTable();
    }

    @Test
    void creationAndDropTest() {
        assertTrue(tabSpon.dropTable());
        assertFalse(tabSpon.dropTable());
        assertTrue(tabSpon.createTable());
        assertFalse(tabSpon.createTable());
    }
    
    @Test
    void saveTest() {
        assertTrue(tabSpon.save(s1));
        assertFalse(tabSpon.save(s1));
        assertTrue(tabSpon.save(s2));
    }
    
    @Test
    void updateTest() {
        assertThrows(UnsupportedOperationException.class, () -> tabSpon.update(s1));
        assertThrows(UnsupportedOperationException.class, () -> tabSpon.update(s2));
    }

    @Test
    void deleteTest() {
    	assertThrows(UnsupportedOperationException.class, () -> tabSpon.delete(s1.getIdSponsor()));
    	assertThrows(UnsupportedOperationException.class, () -> tabSpon.delete(s2.getIdSponsor()));
    }

    @Test
    void findByPrimaryKeyTest() {
    	tabSpon.save(s1);
    	tabSpon.save(s2);
        assertEquals(s1, tabSpon.findByPrimaryKey(s1.getIdSponsor()).orElse(null));
        assertEquals(s2, tabSpon.findByPrimaryKey(s2.getIdSponsor()).orElse(null));
    }

    @Test
    void findAllTest() {
    	tabSpon.save(s1);
    	tabSpon.save(s2);
        List<Sponsor> l = List.of(s1, s2);
        List<Sponsor> l2 = tabSpon.findAll();
        assertTrue(l.size() == l2.size() && l.containsAll(l2) && l2.containsAll(l));
    }
    
    @Test
    void findByNome() {
    	tabSpon.save(s1);
    	tabSpon.save(s2);
        assertEquals(List.of(s1), tabSpon.findByName(s1.getNome()));
        assertEquals(List.of(s2), tabSpon.findByName(s2.getNome()));
    }
}
