import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object HelloScala {

  def main(args: Array[String]): Unit = {


    val spark = SparkSession
      .builder()
      .appName("Sample")
      .master("local[*]")
      .getOrCreate()

    val source = "file:///Users/1002614/tool/spark_data/README.md"
    val df = spark.read.text(source)

    val wordDF = df.select(explode(split(col("value"), " ")).as("word"))
    val result = wordDF.groupBy("word").count

    result.show()
  }

}
