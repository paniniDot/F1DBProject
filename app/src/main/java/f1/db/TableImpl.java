package f1.db;

import java.sql.Connection;
import java.util.Objects;

public abstract class TableImpl<V, K> implements Table<V, K> {
	
	private final Connection connection;
	
	protected TableImpl(final Connection connection) {
		this.connection = Objects.requireNonNull(connection);
	}
	
	public Connection getConnection() {
		return this.connection;
	}
	
	
}
