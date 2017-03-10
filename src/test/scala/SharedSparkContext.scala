package org.template.vanilla

import org.scalatest.{BeforeAndAfterAll, Suite}

import org.apache.spark.{SparkConf, SparkContext}

trait SharedSparkContext extends BeforeAndAfterAll {
  this: Suite =>

  private var _sparkContext: Option[SparkContext] = None
  def sparkContext = _sparkContext.get
  val sparkConf = new SparkConf(false)

  override def beforeAll() {
    _sparkContext = Some(new SparkContext("local", "test", sparkConf))
    super.beforeAll()
  }

  override def afterAll() {
    super.afterAll()
    sparkContext.stop()
    _sparkContext = None
    System.clearProperty("spark.driver.port")
  }
}