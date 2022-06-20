package f1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import f1.db.ConnectionProvider;
import f1.db.tables.TabellaVetture;
import f1.model.Vettura;

class TabellaVettureTest {

	final static String username = "root";
	final static String password = "mattimichi";
	final static String dbName = "f1";

	final static ConnectionProvider connectionProvider = new ConnectionProvider(username, password, dbName);
	final static TabellaVetture tabVet = new TabellaVetture(connectionProvider.getMySQLConnection());

	final Vettura v1 = new Vettura("FRRIV1", "Ferrari V1", 2001, "ABC");
	final Vettura v2 = new Vettura("DUCTIV2", "Ducati V2", 2002, "EFG");

	@BeforeEach
	void setUp() throws Exception {
		tabVet.dropTable();
		tabVet.createTable();
	}

	@AfterEach
	void tearDown() throws Exception {
		tabVet.dropTable();
	}

	@Test
	void creationAndDropTest() {
		assertTrue(tabVet.dropTable());
		assertFalse(tabVet.dropTable());
		assertTrue(tabVet.createTable());
		assertFalse(tabVet.createTable());
	}

	@Test
	void saveTest() {
		assertTrue(tabVet.save(v1));
		assertFalse(tabVet.save(v1));
		assertTrue(tabVet.save(v2));
	}

	@Test
	void updateTest() {
		assertThrows(UnsupportedOperationException.class, () -> tabVet.update(v1));
		assertThrows(UnsupportedOperationException.class, () -> tabVet.update(v2));
	}

	@Test
	void deleteTest() {
		assertThrows(UnsupportedOperationException.class, () -> tabVet.delete(v1.getIdVettura()));
		assertThrows(UnsupportedOperationException.class, () -> tabVet.delete(v2.getIdVettura()));
	}

	@Test
	void findByPrimaryKeyTest() {
		tabVet.save(v1);
		tabVet.save(v2);
		assertEquals(v1, tabVet.findByPrimaryKey(v1.getIdVettura()).orElse(null));
		assertEquals(v2, tabVet.findByPrimaryKey(v2.getIdVettura()).orElse(null));
	}

	@Test
	void findAllTest() {
		tabVet.save(v1);
		tabVet.save(v2);
		List<Vettura> l = List.of(v1, v2);
		List<Vettura> l2 = tabVet.findAll();
		assertTrue(l.size() == l2.size() && l.containsAll(l2) && l2.containsAll(l));
	}

	@Test
	void findByNameTest() {
		Vettura v3 = new Vettura("FRRIV2", "Ferrari V1", 2002, "ABC");
		tabVet.save(v1);
		tabVet.save(v2);
		tabVet.save(v3);
		List<Vettura> l = List.of(v2, v3);
		List<Vettura> l2 = tabVet.findByAnno(v3.getAnnoDiProduzione());
		assertTrue(l.size() == l2.size() && l.containsAll(l2) && l2.containsAll(l));
	}

	@Test
	void findByYearTest() {
		Vettura v3 = new Vettura("FRRIV2", "Ferrari V1", 2002, "ABC");
		tabVet.save(v1);
		tabVet.save(v2);
		tabVet.save(v3);
		List<Vettura> l = List.of(v1, v3);
		List<Vettura> l2 = tabVet.findByNome(v3.getNome());
		assertTrue(l.size() == l2.size() && l.containsAll(l2) && l2.containsAll(l));
	}

	@Test
	void findByNameIdTeam() {
		Vettura v3 = new Vettura("FRRIV2", "Ferrari V1", 2002, "ABC");
		tabVet.save(v1);
		tabVet.save(v2);
		tabVet.save(v3);
		List<Vettura> l = List.of(v1, v3);
		List<Vettura> l2 = tabVet.findByIdTeam(v3.getIdTeamProprietario());
		assertTrue(l.size() == l2.size() && l.containsAll(l2) && l2.containsAll(l));
	}

}
