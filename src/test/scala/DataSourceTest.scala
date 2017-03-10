package org.template.vanilla

import org.scalatest.FlatSpec
import org.scalatest.Matchers

class DataSourceTest
  extends FlatSpec with Matchers with SharedStorageContext with SharedSparkContext  {

  "readTraining" should "return the data" in {
    val dataSource = new DataSource(
      new DataSourceParams(appName = "INVALID_APP_NAME"))
    val data = dataSource.readTraining(sc = sparkContext)
    data shouldBe a [TrainingData]
  }
}