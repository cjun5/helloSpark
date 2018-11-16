import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object WindowTest {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .appName("WindowTest")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    val p1 = ("2017-12-25 12:01:00", "note", 1000)
    val p2 = ("2017-12-25 12:01:10", "pencil", 3500)
    val p3 = ("2017-12-25 12:03:20", "pencil", 23000)
    val p4 = ("2017-12-25 12:05:00", "note", 1500)
    val p5 = ("2017-12-25 12:05:07", "note", 2000)
    val p6 = ("2017-12-25 12:06:25", "note", 1000)
    val p7 = ("2017-12-25 12:08:00", "pencil", 500)
    val p8 = ("2017-12-25 12:09:45", "note", 30000)

    val dd = Seq(p1, p2, p3, p4, p5, p6, p7, p8).toDF("date", "product", "amount")

    dd.groupBy(window(unix_timestamp('date).cast("timestamp"), "5 minutes"), 'product).agg(sum('amount)).show(false)


  }

}
