import org.apache.spark.sql.catalyst.TableIdentifier
import org.apache.spark.sql.{SaveMode, SparkSession}

/**
 * spark 2.4.0 review - Bucket pruning
 * https://www.waitingforcode.com/apache-spark-sql/apache-spark-2.4.0-features-bucket-pruning/read#bucket_pruning_implementation
 */
object BucketTest {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .appName("BucketTest")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    val tableName = s"order${System.currentTimeMillis()}"
    val orders = Seq((1L, "user1"), (2L, "user2"), (3L, "user3"), (4L, "user1"), (5L, "user4"), (6L, "user5"))
      .toDF("order_id", "user_id")

    orders.write.mode(SaveMode.Overwrite).bucketBy(2, "user_id").saveAsTable(tableName)

    val metadata = spark.sessionState.catalog.getTableMetadata(TableIdentifier(tableName))
    println(metadata.bucketSpec)

    val filteredBuckets = spark.sql(s"select * from ${tableName} where user_id = 'user1'")

    println("========== executedPlan ==========")
    println(filteredBuckets.queryExecution.executedPlan.toString())
  }

}
