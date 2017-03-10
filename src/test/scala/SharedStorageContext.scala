package org.template.vanilla

import org.scalatest.{BeforeAndAfterAll, Suite}

import org.h2.tools.{Server => H2Server}
import scalikejdbc._

trait SharedStorageContext extends BeforeAndAfterAll { this: Suite =>

  val _h2Server = H2Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers" )

  override def beforeAll() {
    _h2Server.start

    Class.forName("org.h2.Driver")
    ConnectionPool.singleton("jdbc:h2:tcp://localhost:9092/mem:test", "sa", "")
    implicit val session = AutoSession
    sql"""
    create table if not exists pio_meta_apps (
      id serial not null primary key,
      name text not null,
      description text)
    """.execute.apply()
    sql"""
    insert into pio_meta_apps (id, name, description) 
      values(1, 'INVALID_APP_NAME', 'temporary for tests')
    """.execute.apply()
    sql"""
    create table if not exists pio_meta_accesskey (
      accesskey character varying(64),
      appid integer not null,
      events text)
    """.execute.apply()
    sql"""
    insert into pio_meta_accesskey (accesskey, appid) 
      values('xyxyxy', 1)
    """.execute.apply()
    sql"""
    create table if not exists pio_meta_channels (
      id serial not null primary key,
      name text not null,
      appid integer not null)
    """.execute.apply()
    sql"""
    create table if not exists pio_model_models (
      id character varying(100),
      models bytea not null)
    """.execute.apply()
    sql"""
    create table if not exists pio_event_1 (
      id character varying(32),
      event text not null,
      entitytype text not null,
      entityid text not null,
      targetentitytype text,
      targetentityid text,
      properties text,
      eventtime timestamp default now(),
      eventtimezone character varying(50) not null,
      tags text,
      prid text,
      creationtime timestamp default now(),
      creationtimezone character varying(50) not null)
    """.execute.apply()
    

    super.beforeAll()
  }

  override def afterAll() {
    super.afterAll()

    _h2Server.stop
  }
}