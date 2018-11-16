import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object UdfTest {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .appName("WindowTest")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._


    val fn1 = udf((job: String) => job match {
      case "student" => true
      case _ => false
    })

    val p1 = ("hayoon", 7, "student")
    val p2 = ("sunwoo", 13, "student")
    val p3 = ("hajoo", 5, "kindergartener")

    val df = Seq(p1, p2, p3).toDF("name", "age", "job")

    df.select('name, 'age, 'job, fn1('job)).show

  }

}
