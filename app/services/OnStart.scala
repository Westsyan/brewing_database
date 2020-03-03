package services

import dao.{BacteriaAmpliconDao, FungusAmpliconDao, TransDao}
import javax.inject._
import models.Tables._
import play.api.Logger
import utils.{TableUtils, Utils}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

@Singleton
class OnStart @Inject()(fungusDao: FungusAmpliconDao,
                        bacteriaDao: BacteriaAmpliconDao,
                        transDao: TransDao) {


  Logger.info("开启成功！")

  val fungus: Seq[FungusAmpliconRow] = Await.result(fungusDao.getAll, Duration.Inf)

  TableUtils.fungusJiangxiang = fungus.filter(_.flavor == "jiangxiang")
  TableUtils.fungusJiangxiangSample = TableUtils.fungusJiangxiang.map(x => (x.id, Utils.jsonToMap(x.sample))).toMap

  TableUtils.fungusMixiang = fungus.filter(_.flavor == "mixiang")
  TableUtils.fungusMixiangSample = TableUtils.fungusMixiang.map(x => (x.id, Utils.jsonToMap(x.sample))).toMap

  TableUtils.fungusNongxiang = fungus.filter(_.flavor == "nongxiang")
  TableUtils.fungusNongxiangSample = TableUtils.fungusNongxiang.map(x => (x.id, Utils.jsonToMap(x.sample))).toMap

  TableUtils.fungusQingxiang = fungus.filter(_.flavor == "qingxiang")
  TableUtils.fungusQingxiangSample = TableUtils.fungusQingxiang.map(x => (x.id, Utils.jsonToMap(x.sample))).toMap

  Logger.info("Fungus 添加成功")

  val bacteria: Seq[BacteriaAmpliconRow] = Await.result(bacteriaDao.getAll, Duration.Inf)

  TableUtils.bacteriaJiangxiang = bacteria.filter(_.flavor == "jiangxiang")
  TableUtils.bacteriaJiangxiangSample = TableUtils.bacteriaJiangxiang.map(x => (x.id, Utils.jsonToMap(x.sample))).toMap

  TableUtils.bacteriaMixiang = bacteria.filter(_.flavor == "mixiang")
  TableUtils.bacteriaMixiangSample = TableUtils.bacteriaMixiang.map(x => (x.id, Utils.jsonToMap(x.sample))).toMap

  TableUtils.bacteriaNongxiang = bacteria.filter(_.flavor == "nongxiang")
  TableUtils.bacteriaNongxiangSample = TableUtils.bacteriaNongxiang.map(x => (x.id, Utils.jsonToMap(x.sample))).toMap

  TableUtils.bacteriaQingxiang = bacteria.filter(_.flavor == "qingxiang")
  TableUtils.bacteriaQingxiangSample = TableUtils.bacteriaQingxiang.map(x => (x.id, Utils.jsonToMap(x.sample))).toMap

  Logger.info("Bacteria 添加成功")



  val trans: Seq[TranscriptomeRow] = Await.result(transDao.getAll, Duration.Inf)

  TableUtils.transJiangxiang = trans.filter(_.flavor == "jiangxiang")
  TableUtils.transJiangxiangSample = TableUtils.transJiangxiang.map(x => (x.id, Utils.jsonToMap(x.trans))).toMap



}
