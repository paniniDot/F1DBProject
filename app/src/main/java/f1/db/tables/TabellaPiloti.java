package f1.db.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import f1.db.TableImpl;
import f1.model.Pilota;
import f1.utils.Utils;

public class TabellaPiloti extends TableImpl<Pilota, String>{
	
	private static final String TABLE_NAME = "PILOTI";
	
	public TabellaPiloti(final Connection connection) {
		super(connection);
	}
	
	@Override
	public String getTableName() {
		return TabellaPiloti.TABLE_NAME;
	}

	@Override
	public boolean createTable() {
		final String query = "CREATE TABLE " + TABLE_NAME + " (" +
			"cf CHAR(16) NOT NULL PRIMARY KEY," +
			"nome VARCHAR(50) NOT NULL," +
			"cognome VARCHAR(50) NOT NULL," +
			"dataNascita DATE NOT NULL," +
			"residenza VARCHAR(200) NOT NULL," +
			"campionatiVinti INT NOT NULL," +
			"numeroDiPresenze INT NOT NULL," +
			"gareVinte INT NOT NULL" +
			")";
		try(final Statement statement = super.getConnection().createStatement()) {
			statement.executeUpdate(query);
			return true;
		} catch (final SQLException e) {
			return false;
		}
	}

	@Override
	public boolean dropTable() {
		try (final Statement statement = super.getConnection().createStatement()) {
            statement.executeUpdate("DROP TABLE " + TABLE_NAME);
            return true;
        } catch (final SQLException e) {
            return false;
        }
	}

	@Override
	public Optional<Pilota> findByPrimaryKey(String CF) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE cf = ?";
        try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setString(1, CF);
            final ResultSet resultSet = statement.executeQuery();
            return readPilotaFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	public List<Pilota> findByNameAndSurname(final String nome, final String cognome) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE nome = ?" + " AND cognome = ?";
        try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setString(1, nome);
            statement.setString(2, cognome);
        	final ResultSet resultSet = statement.executeQuery();
            return readPilotaFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Pilota> findAll() {
		final String query = "SELECT * FROM " + TABLE_NAME;
        try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            final ResultSet resultSet = statement.executeQuery();
            return readPilotaFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	public Optional<Pilota> printPilotStatistics(final Pilota pilota) {
		final String query = "SELECT nome, cognome, campionatiVinti, numeroDiPresenze, gareVinte FROM " + TABLE_NAME + " WHERE cf = ?";
		try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setString(1, pilota.getCf());
            System.out.println(statement);
            final ResultSet resultSet = statement.executeQuery();
            return readPilotaFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	@Override
	public boolean save(Pilota pilota) {
		final String query = "INSERT INTO " + TABLE_NAME + "(cf, nome, cognome, dataNascita, residenza, campionatiVinti, numeroDiPresenze, gareVinte) VALUES (?,?,?,?,?,?,?,?)";
		try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setString(1, pilota.getCf());
            statement.setString(2, pilota.getNome());
            statement.setString(3, pilota.getCognome());
            statement.setDate(4, Utils.dateToSqlDate(pilota.getDatanascita()));
            statement.setString(5, pilota.getResidenza());
            statement.setInt(6, 0);
            statement.setInt(7, 0);
            statement.setInt(8, 0);
            System.out.println(statement);
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public boolean update(Pilota updatedValue) {
		throw new UnsupportedOperationException(); 
	}	

	@Override
	public boolean delete(String primaryKey) {
		throw new UnsupportedOperationException();
	}
	
	public boolean updateGareVinte(Pilota pilota) {
		final String query = "UPDATE " + TABLE_NAME + " SET gareVinte = gareVinte + 1 WHERE cf = " + pilota.getCf(); 
		try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.executeUpdate();
            pilota.setGareVinte(pilota.getGareVinte()+1);
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	public boolean updateCampionatiVinti(Pilota pilota) {
		final String query = "UPDATE " + TABLE_NAME + " SET campionatiVinti = campionatiVinti + 1 WHERE cf = " + pilota.getCf(); 
		try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.executeUpdate();
            pilota.setCampionatiVinti(pilota.getCampionatiVinti()+1);
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	public boolean updatePresenzeInGara(Pilota pilota) {
		final String query = "UPDATE " + TABLE_NAME + " SET numeroDiPresenze = numeroDiPresenze + 1 WHERE cf = " + pilota.getCf(); 
		try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.executeUpdate();
            pilota.setNumeroDiPresenze(pilota.getNumeroDiPresenze()+1);
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	private List<Pilota> readPilotaFromResultSet(final ResultSet set) {
		List<Pilota> list = new ArrayList<>();
		try {
            while (set.next()) {
                final String cf = set.getString("cf");
                final String nome = set.getString("nome");
                final String cognome = set.getString("cognome");
                final Date dataNascita = Utils.sqlDateToDate(set.getDate("dataNascita"));
                final String residenza = set.getString("residenza");
            	final Pilota Pilota = new Pilota(cf, nome, cognome, dataNascita, residenza);
                list.add(Pilota);
            }
        } catch (final SQLException e) {}
        return list;
	}



}
