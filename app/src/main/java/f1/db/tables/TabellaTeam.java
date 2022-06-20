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
import f1.model.Team;
import f1.utils.Utils;

public class TabellaTeam extends TableImpl<Team, String>{
	

	private static final String TABLE_NAME = "PILOTI";
	
	public TabellaTeam(final Connection connection) {
		super(connection);
	}
	
	@Override
	public String getTableName() {
		return TabellaTeam.TABLE_NAME;
	}

	@Override
	public boolean createTable() {
		final String query = "CREATE TABLE " + TABLE_NAME + " (" +
			"idTeam CHAR(16) NOT NULL PRIMARY KEY," +
			"nome VARCHAR(50) NOT NULL," +
			"sedeCentrale VARCHAR(50) NOT NULL," +
			"dataEsordio DATE NOT NULL," +
			"gareVinte INT NOT NULL," +
			"campionatiVinti INT NOT NULL" +
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
	public Optional<Team> findByPrimaryKey(String idTeam) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE idTeam = ?";
        try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setString(1, idTeam);
            final ResultSet resultSet = statement.executeQuery();
            return readTeamFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	public List<Team> findByName(final String nome) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE nome = ?";
        try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setString(1, nome);
        	final ResultSet resultSet = statement.executeQuery();
            return readTeamFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Team> findAll() {
		final String query = "SELECT * FROM " + TABLE_NAME;
        try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            final ResultSet resultSet = statement.executeQuery();
            return readTeamFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	public Optional<Team> printTeamStatistics(final Team team) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE idTeam = ?";
		try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setString(1, team.getIdTeam());
            System.out.println(statement);
            final ResultSet resultSet = statement.executeQuery();
            return readTeamFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	@Override
	public boolean save(Team team) {
		final String query = "INSERT INTO " + TABLE_NAME + "(idTeam, nome, sedeCentrale, dataEsordio, residenza, gareVinte, campionatiVinti ) VALUES (?,?,?,?,?,?,?)";
		try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setString(1, team.getIdTeam());
            statement.setString(2, team.getNome());
            statement.setString(3, team.getSedeCentrale());
            statement.setDate(4, Utils.dateToSqlDate(team.getDataEsordio()));
            statement.setInt(5, 0);
            statement.setInt(6, 0);
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
	public boolean update(Team updatedValue) {
		throw new UnsupportedOperationException(); 
	}	

	@Override
	public boolean delete(String primaryKey) {
		throw new UnsupportedOperationException();
	}
	
	public boolean updateGareVinte(Team team) {
		final String query = "UPDATE " + TABLE_NAME + " SET gareVinte = gareVinte + 1 WHERE idTeam = ?"; 
		try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setString(1, team.getIdTeam());
			statement.executeUpdate();
            team.setGareVinte(team.getGareVinte()+1);
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	public boolean updateCampionatiVinti(Team team) {
		final String query = "UPDATE " + TABLE_NAME + " SET campionatiVinti = campionatiVinti + 1 WHERE idTeam = ?"; 
		try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
			statement.setString(1, team.getIdTeam());
			statement.executeUpdate();
            team.setCampionatiVinti(team.getCampionatiVinti()+1);
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	
	private List<Team> readTeamFromResultSet(final ResultSet set) {
		List<Team> list = new ArrayList<>();
		try {
            while (set.next()) {
                final String idTeam = set.getString("idTeam");
                final String nome = set.getString("nome");
                final String sedeCentrale = set.getString("sedeCentrale");
                final Date dataEsordio = Utils.sqlDateToDate(set.getDate("dataEsordio"));
                final int gareVinte = set.getInt("gareVinte");
                final int campionatiVinti = set.getInt("campionatiVinti");
            	final Team Team = new Team(idTeam, nome, sedeCentrale, dataEsordio, gareVinte, campionatiVinti);
                list.add(Team);
            }
        } catch (final SQLException e) {}
        return list;
	}


}
