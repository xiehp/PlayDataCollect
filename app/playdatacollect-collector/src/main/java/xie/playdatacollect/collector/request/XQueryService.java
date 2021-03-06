package xie.playdatacollect.collector.request;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.influxdb.dto.QueryResult;
import retrofit2.Call;
import retrofit2.http.*;

interface XQueryService {

	public static final String U = "u";
	public static final String P = "p";
	public static final String Q = "q";
	public static final String DB = "db";
	public static final String RP = "rp";
	public static final String PRECISION = "precision";
	public static final String CONSISTENCY = "consistency";
	public static final String EPOCH = "epoch";
	public static final String CHUNK_SIZE = "chunk_size";

	@GET("/ping")
	public Call<ResponseBody> ping();

	/**
	 * @param username        u: optional The username for authentication
	 * @param password        p: optional The password for authentication
	 * @param database        db: required The database to write points
	 * @param retentionPolicy rp: optional The retention policy to write points.
	 *                        If not specified, the autogen retention
	 * @param precision       optional The precision of the time stamps (n, u, ms, s, m, h).
	 *                        If not specified, n
	 * @param consistency     optional The write consistency level required for the write to succeed.
	 *                        Can be one of one, any, all, quorum. Defaults to all.
	 */
	@POST("/write")
	public Call<ResponseBody> writePoints(@Query(U) String username,
										  @Query(P) String password, @Query(DB) String database,
										  @Query(RP) String retentionPolicy, @Query(PRECISION) String precision,
										  @Query(CONSISTENCY) String consistency, @Body RequestBody batchPoints);

	@GET("/query")
	public Call<QueryResult> query(@Query(U) String username, @Query(P) String password, @Query(DB) String db,
								   @Query(EPOCH) String epoch, @Query(value = Q, encoded = true) String query);

	@GET("/query")
	public Call<QueryResult> query(@Query(U) String username, @Query(P) String password, @Query(DB) String db,
								   @Query(value = Q, encoded = true) String query);

	@POST("/query")
	public Call<QueryResult> postQuery(@Query(U) String username, @Query(P) String password, @Query(DB) String db,
									   @Query(value = Q, encoded = true) String query);

	@GET("/query")
	public Call<QueryResult> query(@Query(U) String username, @Query(P) String password,
								   @Query(value = Q, encoded = true) String query);

	@POST("/query")
	public Call<QueryResult> postQuery(@Query(U) String username,
									   @Query(P) String password, @Query(value = Q, encoded = true) String query);

	@Streaming
	@GET("/query?chunked=true")
	public Call<ResponseBody> query(@Query(U) String username,
									@Query(P) String password, @Query(DB) String db, @Query(value = Q, encoded = true) String query,
									@Query(CHUNK_SIZE) int chunkSize);
}

