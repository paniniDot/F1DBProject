package f1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import f1.db.ConnectionProvider;
import f1.db.tables.TabellaCampionati;
import f1.model.Campionato;

class TabellaCampionatiTest {

	final static String username = "root";
    final static String password = "mattimichi";
    final static String dbName = "f1";
    
    final static ConnectionProvider connectionProvider = new ConnectionProvider(username, password, dbName);
    final static TabellaCampionati tabCamp = new TabellaCampionati(connectionProvider.getMySQLConnection());

    final Campionato c1 = new Campionato("ABC", 2020, "Campionato 2020", "Il campionato mondiale di Formula 1 2020 organizzato dalla FIA è stato, nella storia della categoria, la 71ª stagione ad assegnare il campionato piloti e la 63ª ad assegnare il campionato costruttori. È iniziato il 5 luglio e si è concluso il 13 dicembre, dopo diciassette gare, quattro in meno rispetto alla stagione precedente.");
    final Campionato c2 = new Campionato("EFG", 2021, "Campionato 2021", "Il campionato mondiale di Formula 1 2021 organizzato dalla FIA è stato, nella storia della categoria, la 72ª stagione ad assegnare il campionato piloti e la 64ª ad assegnare il campionato costruttori. È iniziato il 28 marzo e si è concluso il 12 dicembre, dopo ventidue gare, cinque in più rispetto alla stagione precedente, rendendolo il campionato più lungo di sempre per numero di appuntamenti della storia della Formula 1.");

    @BeforeEach
    void setUp() throws Exception {
    	tabCamp.dropTable();
    	tabCamp.createTable();
    }

    @AfterEach
    void tearDown() throws Exception {
    	tabCamp.dropTable();
    }

    @Test
    void creationAndDropTest() {
        assertTrue(tabCamp.dropTable());
        assertFalse(tabCamp.dropTable());
        assertTrue(tabCamp.createTable());
        assertFalse(tabCamp.createTable());
    }
    
    @Test
    void saveTest() {
        assertTrue(tabCamp.save(c1));
        assertFalse(tabCamp.save(c1));
        assertTrue(tabCamp.save(c2));
    }
    
    @Test
    void updateTest() {
        assertThrows(UnsupportedOperationException.class, () -> tabCamp.update(c1));
        assertThrows(UnsupportedOperationException.class, () -> tabCamp.update(c2));
    }

    @Test
    void deleteTest() {
    	assertThrows(UnsupportedOperationException.class, () -> tabCamp.delete(c1.getIdCampionato()));
    	assertThrows(UnsupportedOperationException.class, () -> tabCamp.delete(c2.getIdCampionato()));
    }

    @Test
    void findByPrimaryKeyTest() {
    	tabCamp.save(c1);
    	tabCamp.save(c2);
        assertEquals(c1, tabCamp.findByPrimaryKey(c1.getIdCampionato()).orElse(null));
        assertEquals(c2, tabCamp.findByPrimaryKey(c2.getIdCampionato()).orElse(null));
    }

    @Test
    void findAllTest() {
    	tabCamp.save(c1);
    	tabCamp.save(c2);
        List<Campionato> l = List.of(c1, c2);
        List<Campionato> l2 = tabCamp.findAll();
        assertTrue(l.size() == l2.size() && l.containsAll(l2) && l2.containsAll(l));
    }
    
    @Test
    void findByNome() {
    	tabCamp.save(c1);
    	tabCamp.save(c2);
        assertEquals(List.of(c1), tabCamp.findByName(c1.getNome()));
        assertEquals(List.of(c2), tabCamp.findByName(c2.getNome()));
    }
    
    @Test
    void findByAnno() {
    	tabCamp.save(c1);
    	tabCamp.save(c2);
        assertEquals(c1, tabCamp.findByYear(2020).orElse(null));
        assertEquals(c2, tabCamp.findByYear(2021).orElse(null));
    }
    
}
