package xie.common.spring.druid.log;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.alibaba.druid.sql.SQLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyDruidLogFilter extends Slf4jLogFilter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void statementExecuteBefore(StatementProxy statement, String sql) {
		printOKSql("before", statement, sql, null);
		super.statementExecuteBefore(statement, sql);
	}

//	@Override
//	protected void statementExecuteAfter(StatementProxy statement, String sql, boolean firstResult) {
//		printOKSql("after", statement, sql, null);
//		super.statementExecuteAfter(statement, sql, firstResult);
//	}

	@Override
	protected void statementExecuteQueryBefore(StatementProxy statement, String sql) {
		printOKSql("QueryBefore", statement, sql, null);
		super.statementExecuteQueryBefore(statement, sql);
	}

	@Override
	protected void statementExecuteUpdateBefore(StatementProxy statement, String sql) {
		printOKSql("updateBefore", statement, sql, null);
		super.statementExecuteUpdateBefore(statement, sql);
	}

	@Override
	protected void statement_executeErrorAfter(StatementProxy statement, String sql, Throwable error) {
		printOKSql("errorAfter", statement, sql, error);
		super.statement_executeErrorAfter(statement, sql, error);
	}

	public void printOKSql(String point, StatementProxy statement, String sql, Throwable error) {
		int parametersSize = statement.getParametersSize();
		List<Object> parameters = new ArrayList<Object>(parametersSize);
		for (int i = 0; i < parametersSize; ++i) {
			JdbcParameter jdbcParam = statement.getParameter(i);
			parameters.add(jdbcParam != null ? jdbcParam.getValue() : null);
		}

		try {
			String dbType = statement.getConnectionProxy().getDirectDataSource().getDbType();
			String formattedSql = SQLUtils.format(sql, dbType, parameters, new SQLUtils.FormatOption(false, false));

			if (point != null) {
				logger.info(point + ":" + formattedSql);
			} else {
				logger.info(formattedSql);
			}
		} catch (Throwable e) {
			logger.info(e.getMessage());
			logger.info(sql + ", " + parameters);
		}

		if (error != null) {

		}
	}
}
