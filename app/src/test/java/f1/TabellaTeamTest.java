package f1;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import f1.db.ConnectionProvider;
import f1.db.tables.TabellaTeam;
import f1.model.Team;
import f1.utils.Utils;

class TabellaTeamTest {
	final static String username = "root";
	final static String password = "mattimichi";
	final static String dbName = "f1";

	final static ConnectionProvider connectionProvider = new ConnectionProvider(username, password, dbName);
	final static TabellaTeam tabTeam = new TabellaTeam(connectionProvider.getMySQLConnection());

	final Team team1 = new Team("ABC", "Ferrari", "Fano", Utils.buildDate(28, 11, 2001).get());
	final Team team2 = new Team("EFG", "Ducati", "Pesaro", Utils.buildDate(12, 1, 2001).get());

	@BeforeEach
	void setUp() throws Exception {
		tabTeam.dropTable();
		tabTeam.createTable();
	}

	@AfterEach
	void tearDown() throws Exception {
		tabTeam.dropTable();
	}

	@Test
	void creationAndDropTest() {
		assertTrue(tabTeam.dropTable());
		assertFalse(tabTeam.dropTable());
		assertTrue(tabTeam.createTable());
		assertFalse(tabTeam.createTable());
	}

	@Test
	void saveTest() {
		assertTrue(tabTeam.save(team1));
		assertFalse(tabTeam.save(team1));
		assertTrue(tabTeam.save(team2));
	}

	@Test
	void updateTest() {
		assertThrows(UnsupportedOperationException.class, () -> tabTeam.update(team1));
		assertThrows(UnsupportedOperationException.class, () -> tabTeam.update(team2));
	}

	@Test
	void deleteTest() {
		assertThrows(UnsupportedOperationException.class, () -> tabTeam.delete(team1.getIdTeam()));
		assertThrows(UnsupportedOperationException.class, () -> tabTeam.delete(team2.getIdTeam()));
	}

	@Test
	void findByPrimaryKeyTest() {
		tabTeam.save(team1);
		tabTeam.save(team2);
		assertEquals(team1, tabTeam.findByPrimaryKey(team1.getIdTeam()).orElse(null));
		assertEquals(team2, tabTeam.findByPrimaryKey(team2.getIdTeam()).orElse(null));
	}

	@Test
	void findAllTest() {
		tabTeam.save(team1);
		tabTeam.save(team2);
		List<Team> l = List.of(team1, team2);
		List<Team> l2 = tabTeam.findAll();
		assertTrue(l.size() == l2.size() && l.containsAll(l2) && l2.containsAll(l));
	}

	@Test
	void statisticsTest() {
		tabTeam.save(team1);
		tabTeam.save(team2);
		assertEquals(tabTeam.printTeamStatistics(team1).orElse(null), team1);
		assertEquals(tabTeam.printTeamStatistics(team2).orElse(null), team2);
	}

	@Test
	void IncrementGareVinteTest() {
		tabTeam.save(team1);
		tabTeam.updateGareVinte(team1);
		tabTeam.updateGareVinte(team1);
		tabTeam.updateGareVinte(team1);
		tabTeam.save(team2);
		tabTeam.updateGareVinte(team2);
		assertEquals(tabTeam.findByPrimaryKey(team1.getIdTeam()).get().getGareVinte(), team1.getGareVinte());
		assertEquals(tabTeam.findByPrimaryKey(team1.getIdTeam()).get().getGareVinte(), 3);
		assertEquals(tabTeam.findByPrimaryKey(team2.getIdTeam()).get().getGareVinte(), team2.getGareVinte());
		assertEquals(tabTeam.findByPrimaryKey(team2.getIdTeam()).get().getGareVinte(), 1);
	}

	@Test
	void IncrementCampionatiVintiTest() {
		tabTeam.save(team1);
		tabTeam.updateCampionatiVinti(team1);
		tabTeam.updateCampionatiVinti(team1);
		tabTeam.updateCampionatiVinti(team1);
		tabTeam.save(team2);
		tabTeam.updateCampionatiVinti(team2);
		assertEquals(tabTeam.findByPrimaryKey(team1.getIdTeam()).get().getCampionatiVinti(),
				team1.getCampionatiVinti());
		assertEquals(tabTeam.findByPrimaryKey(team1.getIdTeam()).get().getCampionatiVinti(), 3);
		assertEquals(tabTeam.findByPrimaryKey(team2.getIdTeam()).get().getCampionatiVinti(),
				team2.getCampionatiVinti());
		assertEquals(tabTeam.findByPrimaryKey(team2.getIdTeam()).get().getCampionatiVinti(), 1);
	}

}