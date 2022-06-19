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

public class TabellaPilota extends TableImpl<Pilota, String>{
	
	private static final String TABLE_NAME = "DIPENDENTI";
	
	public TabellaPilota(final Connection connection) {
		super(connection);
	}
	
	@Override
	public String getTableName() {
		return TabellaPilota.TABLE_NAME;
	}

	@Override
	public boolean createTable() {
		final String query = "CREATE TABLE" + TABLE_NAME + " (" +
			"cf CHAR(16) NOT NULL PRIMARY KEY," +
			"nome VARCHAR(50) NOT NULL," +
			"cognome VARCHAR(50) NOT NULL," +
			"dataNascita DATE NOT NULL," +
			"residenza VARCHAR(200) NOT NULL" +
			"campionatiVint VARCHAR(200) NOT NULL" +
			"numeroDiPresenze VARCHAR(200) NOT NULL" +
			"gareVinte VARCHAR(200) NOT NULL" +
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
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setString(1, CF);
            final ResultSet resultSet = statement.executeQuery();
            return readDipendenteFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Pilota> findAll() {
		final String query = "SELECT * FROM " + TABLE_NAME;
        try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            final ResultSet resultSet = statement.executeQuery();
            return readDipendenteFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Pilota> findByNameAndSurname(final String nome, final String cognome) {
		final String query = "SELECT * FROM " + TABLE_NAME + "WHERE nome = ?" + " AND cognome = ?";
        try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setString(1, nome);
            statement.setString(2, cognome);
        	final ResultSet resultSet = statement.executeQuery();
            return readDipendenteFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	public List<Pilota> printStatistics(final String nome, final String cognome) {
		final String query = "SELECT * FROM " + TABLE_NAME+ "WHERE nome = ?" + " AND cognome = ?" ;
        try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setString(1, nome);
            statement.setString(2, cognome);
        	final ResultSet resultSet = statement.executeQuery();
            return readDipendenteFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	@Override
	public boolean save(Pilota dipendente) {
		final String query = "INSERT INTO " + TABLE_NAME + "(cf, nome, cognome, dataNascita, residenza,CampionatiVinti, NumeroDiPresenze, GareVinte) VALUES (?,?,?,?,?,?,?,?)";
        try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setString(1, dipendente.getCf());
            statement.setString(2, dipendente.getNome());
            statement.setString(3, dipendente.getCognome());
            statement.setDate(4, Utils.dateToSqlDate(dipendente.getDatanascita()));
            statement.setInt(5, dipendente.getCampionatiVinti());
            statement.setInt(6, dipendente.getNumeroDiPresenze());
            statement.setInt(7, dipendente.getGareVinte());
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String primaryKey) {
		throw new UnsupportedOperationException();
	}

	private List<Pilota> readDipendenteFromResultSet(final ResultSet set) {
		List<Pilota> list = new ArrayList<>();
		try {
            while (set.next()) {
                final String cf = set.getString("cf");
                final String nome = set.getString("nome");
                final String cognome = set.getString("cognome");
                final Date dataNascita = Utils.sqlDateToDate(set.getDate("dataNascita"));
                final String residenza = set.getString("residenza");
            	int campionatiVinti = set.getInt("campionatiVinti");
            	int numeroDiPresenze= set.getInt("numeroDiPresenze");;
            	int gareVinte= set.getInt("gareVinte");;
                final Pilota dipendente = new Pilota(cf, nome, cognome, dataNascita, residenza, campionatiVinti, numeroDiPresenze, gareVinte);
                list.add(dipendente);
            }
        } catch (final SQLException e) {}
        return list;
	}



}
