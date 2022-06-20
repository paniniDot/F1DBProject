package f1;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import f1.db.ConnectionProvider;
import f1.db.tables.TabellaPiloti;
import f1.model.Pilota;
import f1.utils.Utils;

class TabellaPilotiTest {
    final static String username = "root";
    final static String password = "mattimichi";
    final static String dbName = "f1";
    
    final static ConnectionProvider connectionProvider = new ConnectionProvider(username, password, dbName);
    final static TabellaPiloti tabPiloti = new TabellaPiloti(connectionProvider.getMySQLConnection());

    final Pilota pil1 = new Pilota("PNNMTT01S28D488Z", "Mattia", "Panni", Utils.buildDate(28, 11, 2001).get(), "Via Rinalducci 65, Fano (PU)");
    final Pilota pil2 = new Pilota("FRNRCD01S29E599A", "Riccardo", "Fiorani", Utils.buildDate(12, 1, 2001).get(), "Via Salamini 6, Fano (PU)");

    @BeforeEach
    void setUp() throws Exception {
    	tabPiloti.dropTable();
    	tabPiloti.createTable();
    }

    @AfterEach
    void tearDown() throws Exception {
    	tabPiloti.dropTable();
    }

    @Test
    void creationAndDropTest() {
        assertTrue(tabPiloti.dropTable());
        assertFalse(tabPiloti.dropTable());
        assertTrue(tabPiloti.createTable());
        assertFalse(tabPiloti.createTable());
    }
    
    @Test
    void saveTest() {
        assertTrue(tabPiloti.save(pil1));
        assertFalse(tabPiloti.save(pil1));
        assertTrue(tabPiloti.save(pil2));
    }
    
    @Test
    void updateTest() {
        assertThrows(UnsupportedOperationException.class, () -> tabPiloti.update(pil1));
        assertThrows(UnsupportedOperationException.class, () -> tabPiloti.update(pil2));
    }

    @Test
    void deleteTest() {
    	assertThrows(UnsupportedOperationException.class, () -> tabPiloti.delete(pil1.getCf()));
    	assertThrows(UnsupportedOperationException.class, () -> tabPiloti.delete(pil2.getCf()));
    }

    @Test
    void findByPrimaryKeyTest() {
    	tabPiloti.save(pil1);
    	tabPiloti.save(pil2);
        assertEquals(pil1, tabPiloti.findByPrimaryKey(pil1.getCf()).orElse(null));
        assertEquals(pil2, tabPiloti.findByPrimaryKey(pil2.getCf()).orElse(null));
    }

    @Test
    void findAllTest() {
    	tabPiloti.save(pil1);
    	tabPiloti.save(pil2);
        List<Pilota> l = List.of(pil1, pil2);
        List<Pilota> l2 = tabPiloti.findAll();
        assertTrue(l.size() == l2.size() && l.containsAll(l2) && l2.containsAll(l));
    }
    
    @Test
    void findByNameAndSurnameTest() {
        final Pilota pil3 = new Pilota("ABCDEFGHIL123456", "Mattia", "Panni", Utils.buildDate(11, 10, 1998).get(), "via Spadoni 3");
        tabPiloti.save(pil1);
        tabPiloti.save(pil2);
        tabPiloti.save(pil3);
    	List<Pilota> l = List.of(pil1, pil3);
    	List<Pilota> l2 = tabPiloti.findByNameAndSurname("Mattia", "Panni");
    	assertTrue(l.size() == l2.size() && l.containsAll(l2) && l2.containsAll(l));
    }
    
    @Test
    void statisticsTest() {
    	tabPiloti.save(pil1);
    	tabPiloti.save(pil2);
    	assertEquals(tabPiloti.printPilotStatistics(pil1).orElse(null), pil1);
    	assertEquals(tabPiloti.printPilotStatistics(pil2).orElse(null), pil2);
    }
    
    @Test
    void IncrementNumeroPresenzeTest() {
    	tabPiloti.save(pil1);
    	tabPiloti.updatePresenzeInGara(pil1);
    	tabPiloti.updatePresenzeInGara(pil1);
    	tabPiloti.updatePresenzeInGara(pil1);
    	tabPiloti.save(pil2);
    	tabPiloti.updatePresenzeInGara(pil2);
    	assertEquals(tabPiloti.findByPrimaryKey(pil1.getCf()).get().getNumeroDiPresenze(), pil1.getNumeroDiPresenze());
    	assertEquals(tabPiloti.findByPrimaryKey(pil1.getCf()).get().getNumeroDiPresenze(), 3);
    	assertEquals(tabPiloti.findByPrimaryKey(pil2.getCf()).get().getNumeroDiPresenze(), pil2.getNumeroDiPresenze());
    	assertEquals(tabPiloti.findByPrimaryKey(pil2.getCf()).get().getNumeroDiPresenze(), 1);
    }
    
    @Test
    void IncrementGareVinteTest() {
    	tabPiloti.save(pil1);
    	tabPiloti.updateGareVinte(pil1);
    	tabPiloti.updateGareVinte(pil1);
    	tabPiloti.updateGareVinte(pil1);
    	tabPiloti.save(pil2);
    	tabPiloti.updateGareVinte(pil2);
    	assertEquals(tabPiloti.findByPrimaryKey(pil1.getCf()).get().getGareVinte(), pil1.getGareVinte());
    	assertEquals(tabPiloti.findByPrimaryKey(pil1.getCf()).get().getGareVinte(), 3);
    	assertEquals(tabPiloti.findByPrimaryKey(pil2.getCf()).get().getGareVinte(), pil2.getGareVinte());
    	assertEquals(tabPiloti.findByPrimaryKey(pil2.getCf()).get().getGareVinte(), 1);
    }
    
    @Test
    void IncrementCampionatiVintiTest() {
    	tabPiloti.save(pil1);
    	tabPiloti.updateCampionatiVinti(pil1);
    	tabPiloti.updateCampionatiVinti(pil1);
    	tabPiloti.updateCampionatiVinti(pil1);
    	tabPiloti.save(pil2);
    	tabPiloti.updateCampionatiVinti(pil2);
    	assertEquals(tabPiloti.findByPrimaryKey(pil1.getCf()).get().getCampionatiVinti(), pil1.getCampionatiVinti());
    	assertEquals(tabPiloti.findByPrimaryKey(pil1.getCf()).get().getCampionatiVinti(), 3);
    	assertEquals(tabPiloti.findByPrimaryKey(pil2.getCf()).get().getCampionatiVinti(), pil2.getCampionatiVinti());
    	assertEquals(tabPiloti.findByPrimaryKey(pil2.getCf()).get().getCampionatiVinti(), 1);
    }
    
}