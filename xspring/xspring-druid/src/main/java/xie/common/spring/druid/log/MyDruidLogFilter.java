package xie.common.spring.druid.log;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.alibaba.druid.sql.SQLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xie.common.utils.string.XStringUtils;
import xie.framework.core.service.dictionary.utils.PublicDictionaryLoader;

import java.util.ArrayList;
import java.util.List;

import static xie.framework.core.service.dictionary.common.PublicDictionaryConst.DictionaryCodeSystem;

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
		// 过滤不需要打印的sql
		boolean printSqlFlag = PublicDictionaryLoader.getSystemBooleanValue(DictionaryCodeSystem.MY_DRUID_PRINT_SQL_FLAG.name());
		if (!printSqlFlag) {
			return;
		}
		String[] excludeList = PublicDictionaryLoader.getSystemArrayValue(DictionaryCodeSystem.MY_DRUID_PRINT_SQL_EXCLUDE.name());
		if (excludeList != null && excludeList.length > 0) {
			if (XStringUtils.containWithIgnoreCase(sql, excludeList)) {
				return;
			}
		}

		// 打印
		int parametersSize = statement.getParametersSize();
		List<Object> parameters = new ArrayList<>(parametersSize);
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
