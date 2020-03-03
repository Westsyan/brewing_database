package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.MySQLProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import com.github.tototoshi.slick.MySQLJodaSupport._
  import org.joda.time.DateTime
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Array(`16s`.schema, BacteriaAmplicon.schema, Cas.schema, Flavor.schema, Flavordata.schema, FungusAmplicon.schema, Its.schema, Macro.schema, Metagenome.schema, Pacdata.schema, Pcdata.schema, Strain.schema, Straininfo.schema, Strainphe.schema, Transcriptome.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table `16s`
   *  @param id Database column id SqlType(INT), AutoInc
   *  @param `16s` Database column 16s SqlType(VARCHAR), Length(255,true)
   *  @param data Database column data SqlType(TEXT) */
  case class `16sRow`(id: Int, `16s`: String, data: String)
  /** GetResult implicit for fetching `16sRow` objects using plain SQL queries */
  implicit def GetResult16sRow(implicit e0: GR[Int], e1: GR[String]): GR[`16sRow`] = GR{
    prs => import prs._
    `16sRow`.tupled((<<[Int], <<[String], <<[String]))
  }
  /** Table description of table 16s. Objects of this class serve as prototypes for rows in queries. */
  class `16s`(_tableTag: Tag) extends profile.api.Table[`16sRow`](_tableTag, Some("brewing_database"), "16s") {
    def * = (id, `16s`, data) <> (`16sRow`.tupled, `16sRow`.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(`16s`), Rep.Some(data))).shaped.<>({r=>import r._; _1.map(_=> `16sRow`.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column 16s SqlType(VARCHAR), Length(255,true) */
    val `16s`: Rep[String] = column[String]("16s", O.Length(255,varying=true))
    /** Database column data SqlType(TEXT) */
    val data: Rep[String] = column[String]("data")

    /** Primary key of `16s` (database name 16s_PK) */
    val pk = primaryKey("16s_PK", (id, `16s`))
  }
  /** Collection-like TableQuery object for table `16s` */
  lazy val `16s` = new TableQuery(tag => new `16s`(tag))

  /** Entity class storing rows of table BacteriaAmplicon
   *  @param id Database column id SqlType(INT), AutoInc
   *  @param flavor Database column flavor SqlType(VARCHAR), Length(255,true)
   *  @param len Database column len SqlType(TEXT)
   *  @param kingdom Database column kingdom SqlType(TEXT)
   *  @param phylum Database column phylum SqlType(TEXT)
   *  @param `class` Database column class SqlType(TEXT)
   *  @param order Database column order SqlType(TEXT)
   *  @param family Database column family SqlType(TEXT)
   *  @param genus Database column genus SqlType(TEXT)
   *  @param species Database column species SqlType(TEXT)
   *  @param sample Database column sample SqlType(TEXT)
   *  @param seq Database column seq SqlType(TEXT) */
  case class BacteriaAmpliconRow(id: Int, flavor: String, len: String, kingdom: String, phylum: String, `class`: String, order: String, family: String, genus: String, species: String, sample: String, seq: String)
  /** GetResult implicit for fetching BacteriaAmpliconRow objects using plain SQL queries */
  implicit def GetResultBacteriaAmpliconRow(implicit e0: GR[Int], e1: GR[String]): GR[BacteriaAmpliconRow] = GR{
    prs => import prs._
    BacteriaAmpliconRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table bacteria_amplicon. Objects of this class serve as prototypes for rows in queries.
   *  NOTE: The following names collided with Scala keywords and were escaped: class */
  class BacteriaAmplicon(_tableTag: Tag) extends profile.api.Table[BacteriaAmpliconRow](_tableTag, Some("brewing_database"), "bacteria_amplicon") {
    def * = (id, flavor, len, kingdom, phylum, `class`, order, family, genus, species, sample, seq) <> (BacteriaAmpliconRow.tupled, BacteriaAmpliconRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(flavor), Rep.Some(len), Rep.Some(kingdom), Rep.Some(phylum), Rep.Some(`class`), Rep.Some(order), Rep.Some(family), Rep.Some(genus), Rep.Some(species), Rep.Some(sample), Rep.Some(seq))).shaped.<>({r=>import r._; _1.map(_=> BacteriaAmpliconRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get, _12.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column flavor SqlType(VARCHAR), Length(255,true) */
    val flavor: Rep[String] = column[String]("flavor", O.Length(255,varying=true))
    /** Database column len SqlType(TEXT) */
    val len: Rep[String] = column[String]("len")
    /** Database column kingdom SqlType(TEXT) */
    val kingdom: Rep[String] = column[String]("kingdom")
    /** Database column phylum SqlType(TEXT) */
    val phylum: Rep[String] = column[String]("phylum")
    /** Database column class SqlType(TEXT)
     *  NOTE: The name was escaped because it collided with a Scala keyword. */
    val `class`: Rep[String] = column[String]("class")
    /** Database column order SqlType(TEXT) */
    val order: Rep[String] = column[String]("order")
    /** Database column family SqlType(TEXT) */
    val family: Rep[String] = column[String]("family")
    /** Database column genus SqlType(TEXT) */
    val genus: Rep[String] = column[String]("genus")
    /** Database column species SqlType(TEXT) */
    val species: Rep[String] = column[String]("species")
    /** Database column sample SqlType(TEXT) */
    val sample: Rep[String] = column[String]("sample")
    /** Database column seq SqlType(TEXT) */
    val seq: Rep[String] = column[String]("seq")

    /** Primary key of BacteriaAmplicon (database name bacteria_amplicon_PK) */
    val pk = primaryKey("bacteria_amplicon_PK", (id, flavor))
  }
  /** Collection-like TableQuery object for table BacteriaAmplicon */
  lazy val BacteriaAmplicon = new TableQuery(tag => new BacteriaAmplicon(tag))

  /** Entity class storing rows of table Cas
   *  @param id Database column id SqlType(INT), AutoInc
   *  @param cas Database column cas SqlType(VARCHAR), Length(255,true)
   *  @param name Database column name SqlType(TEXT)
   *  @param molecular Database column Molecular SqlType(TEXT)
   *  @param a Database column A SqlType(TEXT)
   *  @param b Database column B SqlType(TEXT)
   *  @param c Database column C SqlType(TEXT)
   *  @param d Database column D SqlType(TEXT)
   *  @param e Database column E SqlType(TEXT)
   *  @param f Database column F SqlType(TEXT)
   *  @param g Database column G SqlType(TEXT)
   *  @param h Database column H SqlType(TEXT)
   *  @param i Database column I SqlType(TEXT)
   *  @param j Database column J SqlType(TEXT)
   *  @param k Database column K SqlType(TEXT)
   *  @param l Database column L SqlType(TEXT)
   *  @param m Database column M SqlType(TEXT)
   *  @param n Database column N SqlType(TEXT)
   *  @param o Database column O SqlType(TEXT)
   *  @param p Database column P SqlType(TEXT) */
  case class CasRow(id: Int, cas: String, name: String, molecular: String, a: String, b: String, c: String, d: String, e: String, f: String, g: String, h: String, i: String, j: String, k: String, l: String, m: String, n: String, o: String, p: String)
  /** GetResult implicit for fetching CasRow objects using plain SQL queries */
  implicit def GetResultCasRow(implicit e0: GR[Int], e1: GR[String]): GR[CasRow] = GR{
    prs => import prs._
    CasRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table cas. Objects of this class serve as prototypes for rows in queries. */
  class Cas(_tableTag: Tag) extends profile.api.Table[CasRow](_tableTag, Some("brewing_database"), "cas") {
    def * = (id, cas, name, molecular, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p) <> (CasRow.tupled, CasRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(cas), Rep.Some(name), Rep.Some(molecular), Rep.Some(a), Rep.Some(b), Rep.Some(c), Rep.Some(d), Rep.Some(e), Rep.Some(f), Rep.Some(g), Rep.Some(h), Rep.Some(i), Rep.Some(j), Rep.Some(k), Rep.Some(l), Rep.Some(m), Rep.Some(n), Rep.Some(o), Rep.Some(p))).shaped.<>({r=>import r._; _1.map(_=> CasRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get, _12.get, _13.get, _14.get, _15.get, _16.get, _17.get, _18.get, _19.get, _20.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column cas SqlType(VARCHAR), Length(255,true) */
    val cas: Rep[String] = column[String]("cas", O.Length(255,varying=true))
    /** Database column name SqlType(TEXT) */
    val name: Rep[String] = column[String]("name")
    /** Database column Molecular SqlType(TEXT) */
    val molecular: Rep[String] = column[String]("Molecular")
    /** Database column A SqlType(TEXT) */
    val a: Rep[String] = column[String]("A")
    /** Database column B SqlType(TEXT) */
    val b: Rep[String] = column[String]("B")
    /** Database column C SqlType(TEXT) */
    val c: Rep[String] = column[String]("C")
    /** Database column D SqlType(TEXT) */
    val d: Rep[String] = column[String]("D")
    /** Database column E SqlType(TEXT) */
    val e: Rep[String] = column[String]("E")
    /** Database column F SqlType(TEXT) */
    val f: Rep[String] = column[String]("F")
    /** Database column G SqlType(TEXT) */
    val g: Rep[String] = column[String]("G")
    /** Database column H SqlType(TEXT) */
    val h: Rep[String] = column[String]("H")
    /** Database column I SqlType(TEXT) */
    val i: Rep[String] = column[String]("I")
    /** Database column J SqlType(TEXT) */
    val j: Rep[String] = column[String]("J")
    /** Database column K SqlType(TEXT) */
    val k: Rep[String] = column[String]("K")
    /** Database column L SqlType(TEXT) */
    val l: Rep[String] = column[String]("L")
    /** Database column M SqlType(TEXT) */
    val m: Rep[String] = column[String]("M")
    /** Database column N SqlType(TEXT) */
    val n: Rep[String] = column[String]("N")
    /** Database column O SqlType(TEXT) */
    val o: Rep[String] = column[String]("O")
    /** Database column P SqlType(TEXT) */
    val p: Rep[String] = column[String]("P")

    /** Primary key of Cas (database name cas_PK) */
    val pk = primaryKey("cas_PK", (id, cas))
  }
  /** Collection-like TableQuery object for table Cas */
  lazy val Cas = new TableQuery(tag => new Cas(tag))

  /** Entity class storing rows of table Flavor
   *  @param id Database column id SqlType(INT), AutoInc
   *  @param cas Database column cas SqlType(VARCHAR), Length(255,true)
   *  @param types Database column types SqlType(TEXT)
   *  @param chinese Database column chinese SqlType(TEXT)
   *  @param english Database column english SqlType(TEXT)
   *  @param pcdata Database column pcdata SqlType(TEXT)
   *  @param flavor Database column flavor SqlType(TEXT)
   *  @param func Database column func SqlType(TEXT)
   *  @param wine Database column wine SqlType(TEXT) */
  case class FlavorRow(id: Int, cas: String, types: String, chinese: String, english: String, pcdata: String, flavor: String, func: String, wine: String)
  /** GetResult implicit for fetching FlavorRow objects using plain SQL queries */
  implicit def GetResultFlavorRow(implicit e0: GR[Int], e1: GR[String]): GR[FlavorRow] = GR{
    prs => import prs._
    FlavorRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table flavor. Objects of this class serve as prototypes for rows in queries. */
  class Flavor(_tableTag: Tag) extends profile.api.Table[FlavorRow](_tableTag, Some("brewing_database"), "flavor") {
    def * = (id, cas, types, chinese, english, pcdata, flavor, func, wine) <> (FlavorRow.tupled, FlavorRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(cas), Rep.Some(types), Rep.Some(chinese), Rep.Some(english), Rep.Some(pcdata), Rep.Some(flavor), Rep.Some(func), Rep.Some(wine))).shaped.<>({r=>import r._; _1.map(_=> FlavorRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column cas SqlType(VARCHAR), Length(255,true) */
    val cas: Rep[String] = column[String]("cas", O.Length(255,varying=true))
    /** Database column types SqlType(TEXT) */
    val types: Rep[String] = column[String]("types")
    /** Database column chinese SqlType(TEXT) */
    val chinese: Rep[String] = column[String]("chinese")
    /** Database column english SqlType(TEXT) */
    val english: Rep[String] = column[String]("english")
    /** Database column pcdata SqlType(TEXT) */
    val pcdata: Rep[String] = column[String]("pcdata")
    /** Database column flavor SqlType(TEXT) */
    val flavor: Rep[String] = column[String]("flavor")
    /** Database column func SqlType(TEXT) */
    val func: Rep[String] = column[String]("func")
    /** Database column wine SqlType(TEXT) */
    val wine: Rep[String] = column[String]("wine")

    /** Primary key of Flavor (database name flavor_PK) */
    val pk = primaryKey("flavor_PK", (id, cas))
  }
  /** Collection-like TableQuery object for table Flavor */
  lazy val Flavor = new TableQuery(tag => new Flavor(tag))

  /** Entity class storing rows of table Flavordata
   *  @param id Database column id SqlType(INT), AutoInc
   *  @param cas Database column cas SqlType(VARCHAR), Length(255,true)
   *  @param flavor Database column flavor SqlType(TEXT)
   *  @param english Database column english SqlType(TEXT)
   *  @param chinese Database column chinese SqlType(TEXT)
   *  @param formula Database column formula SqlType(TEXT)
   *  @param weight Database column weight SqlType(TEXT)
   *  @param struction Database column struction SqlType(TEXT)
   *  @param boiling Database column boiling SqlType(TEXT)
   *  @param melting Database column melting SqlType(TEXT)
   *  @param ppm Database column ppm SqlType(TEXT) */
  case class FlavordataRow(id: Int, cas: String, flavor: String, english: String, chinese: String, formula: String, weight: String, struction: String, boiling: String, melting: String, ppm: String)
  /** GetResult implicit for fetching FlavordataRow objects using plain SQL queries */
  implicit def GetResultFlavordataRow(implicit e0: GR[Int], e1: GR[String]): GR[FlavordataRow] = GR{
    prs => import prs._
    FlavordataRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table flavordata. Objects of this class serve as prototypes for rows in queries. */
  class Flavordata(_tableTag: Tag) extends profile.api.Table[FlavordataRow](_tableTag, Some("brewing_database"), "flavordata") {
    def * = (id, cas, flavor, english, chinese, formula, weight, struction, boiling, melting, ppm) <> (FlavordataRow.tupled, FlavordataRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(cas), Rep.Some(flavor), Rep.Some(english), Rep.Some(chinese), Rep.Some(formula), Rep.Some(weight), Rep.Some(struction), Rep.Some(boiling), Rep.Some(melting), Rep.Some(ppm))).shaped.<>({r=>import r._; _1.map(_=> FlavordataRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column cas SqlType(VARCHAR), Length(255,true) */
    val cas: Rep[String] = column[String]("cas", O.Length(255,varying=true))
    /** Database column flavor SqlType(TEXT) */
    val flavor: Rep[String] = column[String]("flavor")
    /** Database column english SqlType(TEXT) */
    val english: Rep[String] = column[String]("english")
    /** Database column chinese SqlType(TEXT) */
    val chinese: Rep[String] = column[String]("chinese")
    /** Database column formula SqlType(TEXT) */
    val formula: Rep[String] = column[String]("formula")
    /** Database column weight SqlType(TEXT) */
    val weight: Rep[String] = column[String]("weight")
    /** Database column struction SqlType(TEXT) */
    val struction: Rep[String] = column[String]("struction")
    /** Database column boiling SqlType(TEXT) */
    val boiling: Rep[String] = column[String]("boiling")
    /** Database column melting SqlType(TEXT) */
    val melting: Rep[String] = column[String]("melting")
    /** Database column ppm SqlType(TEXT) */
    val ppm: Rep[String] = column[String]("ppm")

    /** Primary key of Flavordata (database name flavordata_PK) */
    val pk = primaryKey("flavordata_PK", (id, cas))
  }
  /** Collection-like TableQuery object for table Flavordata */
  lazy val Flavordata = new TableQuery(tag => new Flavordata(tag))

  /** Entity class storing rows of table FungusAmplicon
   *  @param id Database column id SqlType(INT), AutoInc
   *  @param flavor Database column flavor SqlType(VARCHAR), Length(255,true)
   *  @param len Database column len SqlType(TEXT)
   *  @param kingdom Database column kingdom SqlType(TEXT)
   *  @param phylum Database column phylum SqlType(TEXT)
   *  @param `class` Database column class SqlType(TEXT)
   *  @param order Database column order SqlType(TEXT)
   *  @param family Database column family SqlType(TEXT)
   *  @param genus Database column genus SqlType(TEXT)
   *  @param species Database column species SqlType(TEXT)
   *  @param sample Database column sample SqlType(TEXT)
   *  @param seq Database column seq SqlType(TEXT) */
  case class FungusAmpliconRow(id: Int, flavor: String, len: String, kingdom: String, phylum: String, `class`: String, order: String, family: String, genus: String, species: String, sample: String, seq: String)
  /** GetResult implicit for fetching FungusAmpliconRow objects using plain SQL queries */
  implicit def GetResultFungusAmpliconRow(implicit e0: GR[Int], e1: GR[String]): GR[FungusAmpliconRow] = GR{
    prs => import prs._
    FungusAmpliconRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table fungus_amplicon. Objects of this class serve as prototypes for rows in queries.
   *  NOTE: The following names collided with Scala keywords and were escaped: class */
  class FungusAmplicon(_tableTag: Tag) extends profile.api.Table[FungusAmpliconRow](_tableTag, Some("brewing_database"), "fungus_amplicon") {
    def * = (id, flavor, len, kingdom, phylum, `class`, order, family, genus, species, sample, seq) <> (FungusAmpliconRow.tupled, FungusAmpliconRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(flavor), Rep.Some(len), Rep.Some(kingdom), Rep.Some(phylum), Rep.Some(`class`), Rep.Some(order), Rep.Some(family), Rep.Some(genus), Rep.Some(species), Rep.Some(sample), Rep.Some(seq))).shaped.<>({r=>import r._; _1.map(_=> FungusAmpliconRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get, _12.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column flavor SqlType(VARCHAR), Length(255,true) */
    val flavor: Rep[String] = column[String]("flavor", O.Length(255,varying=true))
    /** Database column len SqlType(TEXT) */
    val len: Rep[String] = column[String]("len")
    /** Database column kingdom SqlType(TEXT) */
    val kingdom: Rep[String] = column[String]("kingdom")
    /** Database column phylum SqlType(TEXT) */
    val phylum: Rep[String] = column[String]("phylum")
    /** Database column class SqlType(TEXT)
     *  NOTE: The name was escaped because it collided with a Scala keyword. */
    val `class`: Rep[String] = column[String]("class")
    /** Database column order SqlType(TEXT) */
    val order: Rep[String] = column[String]("order")
    /** Database column family SqlType(TEXT) */
    val family: Rep[String] = column[String]("family")
    /** Database column genus SqlType(TEXT) */
    val genus: Rep[String] = column[String]("genus")
    /** Database column species SqlType(TEXT) */
    val species: Rep[String] = column[String]("species")
    /** Database column sample SqlType(TEXT) */
    val sample: Rep[String] = column[String]("sample")
    /** Database column seq SqlType(TEXT) */
    val seq: Rep[String] = column[String]("seq")

    /** Primary key of FungusAmplicon (database name fungus_amplicon_PK) */
    val pk = primaryKey("fungus_amplicon_PK", (id, flavor))
  }
  /** Collection-like TableQuery object for table FungusAmplicon */
  lazy val FungusAmplicon = new TableQuery(tag => new FungusAmplicon(tag))

  /** Entity class storing rows of table Its
   *  @param id Database column id SqlType(INT), AutoInc
   *  @param its Database column its SqlType(VARCHAR), Length(255,true)
   *  @param data Database column data SqlType(TEXT) */
  case class ItsRow(id: Int, its: String, data: String)
  /** GetResult implicit for fetching ItsRow objects using plain SQL queries */
  implicit def GetResultItsRow(implicit e0: GR[Int], e1: GR[String]): GR[ItsRow] = GR{
    prs => import prs._
    ItsRow.tupled((<<[Int], <<[String], <<[String]))
  }
  /** Table description of table its. Objects of this class serve as prototypes for rows in queries. */
  class Its(_tableTag: Tag) extends profile.api.Table[ItsRow](_tableTag, Some("brewing_database"), "its") {
    def * = (id, its, data) <> (ItsRow.tupled, ItsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(its), Rep.Some(data))).shaped.<>({r=>import r._; _1.map(_=> ItsRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column its SqlType(VARCHAR), Length(255,true) */
    val its: Rep[String] = column[String]("its", O.Length(255,varying=true))
    /** Database column data SqlType(TEXT) */
    val data: Rep[String] = column[String]("data")

    /** Primary key of Its (database name its_PK) */
    val pk = primaryKey("its_PK", (id, its))
  }
  /** Collection-like TableQuery object for table Its */
  lazy val Its = new TableQuery(tag => new Its(tag))

  /** Entity class storing rows of table Macro
   *  @param id Database column id SqlType(INT), AutoInc
   *  @param name Database column name SqlType(VARCHAR), Length(255,true)
   *  @param data Database column data SqlType(TEXT) */
  case class MacroRow(id: Int, name: String, data: String)
  /** GetResult implicit for fetching MacroRow objects using plain SQL queries */
  implicit def GetResultMacroRow(implicit e0: GR[Int], e1: GR[String]): GR[MacroRow] = GR{
    prs => import prs._
    MacroRow.tupled((<<[Int], <<[String], <<[String]))
  }
  /** Table description of table macro. Objects of this class serve as prototypes for rows in queries. */
  class Macro(_tableTag: Tag) extends profile.api.Table[MacroRow](_tableTag, Some("brewing_database"), "macro") {
    def * = (id, name, data) <> (MacroRow.tupled, MacroRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(name), Rep.Some(data))).shaped.<>({r=>import r._; _1.map(_=> MacroRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column name SqlType(VARCHAR), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column data SqlType(TEXT) */
    val data: Rep[String] = column[String]("data")

    /** Primary key of Macro (database name macro_PK) */
    val pk = primaryKey("macro_PK", (id, name))
  }
  /** Collection-like TableQuery object for table Macro */
  lazy val Macro = new TableQuery(tag => new Macro(tag))

  /** Entity class storing rows of table Metagenome
   *  @param id Database column id SqlType(INT), AutoInc
   *  @param code Database column code SqlType(VARCHAR), Length(255,true)
   *  @param flavor Database column flavor SqlType(TEXT)
   *  @param source Database column source SqlType(TEXT)
   *  @param sampleCode Database column sample_code SqlType(TEXT)
   *  @param textCode Database column text_code SqlType(TEXT)
   *  @param sampleInfo Database column sample_info SqlType(TEXT)
   *  @param time Database column time SqlType(TEXT)
   *  @param location Database column location SqlType(TEXT)
   *  @param dataType Database column data_type SqlType(TEXT)
   *  @param seqPlat Database column seq_plat SqlType(TEXT)
   *  @param seqMethod Database column seq_method SqlType(TEXT)
   *  @param primer Database column primer SqlType(TEXT)
   *  @param seqType Database column seq_type SqlType(TEXT)
   *  @param textInfo Database column text_info SqlType(TEXT) */
  case class MetagenomeRow(id: Int, code: String, flavor: String, source: String, sampleCode: String, textCode: String, sampleInfo: String, time: String, location: String, dataType: String, seqPlat: String, seqMethod: String, primer: String, seqType: String, textInfo: String)
  /** GetResult implicit for fetching MetagenomeRow objects using plain SQL queries */
  implicit def GetResultMetagenomeRow(implicit e0: GR[Int], e1: GR[String]): GR[MetagenomeRow] = GR{
    prs => import prs._
    MetagenomeRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table metagenome. Objects of this class serve as prototypes for rows in queries. */
  class Metagenome(_tableTag: Tag) extends profile.api.Table[MetagenomeRow](_tableTag, Some("brewing_database"), "metagenome") {
    def * = (id, code, flavor, source, sampleCode, textCode, sampleInfo, time, location, dataType, seqPlat, seqMethod, primer, seqType, textInfo) <> (MetagenomeRow.tupled, MetagenomeRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(code), Rep.Some(flavor), Rep.Some(source), Rep.Some(sampleCode), Rep.Some(textCode), Rep.Some(sampleInfo), Rep.Some(time), Rep.Some(location), Rep.Some(dataType), Rep.Some(seqPlat), Rep.Some(seqMethod), Rep.Some(primer), Rep.Some(seqType), Rep.Some(textInfo))).shaped.<>({r=>import r._; _1.map(_=> MetagenomeRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get, _12.get, _13.get, _14.get, _15.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column code SqlType(VARCHAR), Length(255,true) */
    val code: Rep[String] = column[String]("code", O.Length(255,varying=true))
    /** Database column flavor SqlType(TEXT) */
    val flavor: Rep[String] = column[String]("flavor")
    /** Database column source SqlType(TEXT) */
    val source: Rep[String] = column[String]("source")
    /** Database column sample_code SqlType(TEXT) */
    val sampleCode: Rep[String] = column[String]("sample_code")
    /** Database column text_code SqlType(TEXT) */
    val textCode: Rep[String] = column[String]("text_code")
    /** Database column sample_info SqlType(TEXT) */
    val sampleInfo: Rep[String] = column[String]("sample_info")
    /** Database column time SqlType(TEXT) */
    val time: Rep[String] = column[String]("time")
    /** Database column location SqlType(TEXT) */
    val location: Rep[String] = column[String]("location")
    /** Database column data_type SqlType(TEXT) */
    val dataType: Rep[String] = column[String]("data_type")
    /** Database column seq_plat SqlType(TEXT) */
    val seqPlat: Rep[String] = column[String]("seq_plat")
    /** Database column seq_method SqlType(TEXT) */
    val seqMethod: Rep[String] = column[String]("seq_method")
    /** Database column primer SqlType(TEXT) */
    val primer: Rep[String] = column[String]("primer")
    /** Database column seq_type SqlType(TEXT) */
    val seqType: Rep[String] = column[String]("seq_type")
    /** Database column text_info SqlType(TEXT) */
    val textInfo: Rep[String] = column[String]("text_info")

    /** Primary key of Metagenome (database name metagenome_PK) */
    val pk = primaryKey("metagenome_PK", (id, code))
  }
  /** Collection-like TableQuery object for table Metagenome */
  lazy val Metagenome = new TableQuery(tag => new Metagenome(tag))

  /** Entity class storing rows of table Pacdata
   *  @param id Database column id SqlType(INT), AutoInc
   *  @param sample Database column sample SqlType(VARCHAR), Length(255,true)
   *  @param acidity Database column acidity SqlType(TEXT)
   *  @param ph Database column ph SqlType(TEXT)
   *  @param sugar Database column sugar SqlType(TEXT)
   *  @param moisture Database column moisture SqlType(TEXT)
   *  @param alcohol Database column alcohol SqlType(TEXT)
   *  @param aceticAcid Database column acetic_acid SqlType(TEXT)
   *  @param lacticAcid Database column lactic_acid SqlType(TEXT)
   *  @param temp Database column temp SqlType(TEXT) */
  case class PacdataRow(id: Int, sample: String, acidity: String, ph: String, sugar: String, moisture: String, alcohol: String, aceticAcid: String, lacticAcid: String, temp: String)
  /** GetResult implicit for fetching PacdataRow objects using plain SQL queries */
  implicit def GetResultPacdataRow(implicit e0: GR[Int], e1: GR[String]): GR[PacdataRow] = GR{
    prs => import prs._
    PacdataRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table pacdata. Objects of this class serve as prototypes for rows in queries. */
  class Pacdata(_tableTag: Tag) extends profile.api.Table[PacdataRow](_tableTag, Some("brewing_database"), "pacdata") {
    def * = (id, sample, acidity, ph, sugar, moisture, alcohol, aceticAcid, lacticAcid, temp) <> (PacdataRow.tupled, PacdataRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(sample), Rep.Some(acidity), Rep.Some(ph), Rep.Some(sugar), Rep.Some(moisture), Rep.Some(alcohol), Rep.Some(aceticAcid), Rep.Some(lacticAcid), Rep.Some(temp))).shaped.<>({r=>import r._; _1.map(_=> PacdataRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column sample SqlType(VARCHAR), Length(255,true) */
    val sample: Rep[String] = column[String]("sample", O.Length(255,varying=true))
    /** Database column acidity SqlType(TEXT) */
    val acidity: Rep[String] = column[String]("acidity")
    /** Database column ph SqlType(TEXT) */
    val ph: Rep[String] = column[String]("ph")
    /** Database column sugar SqlType(TEXT) */
    val sugar: Rep[String] = column[String]("sugar")
    /** Database column moisture SqlType(TEXT) */
    val moisture: Rep[String] = column[String]("moisture")
    /** Database column alcohol SqlType(TEXT) */
    val alcohol: Rep[String] = column[String]("alcohol")
    /** Database column acetic_acid SqlType(TEXT) */
    val aceticAcid: Rep[String] = column[String]("acetic_acid")
    /** Database column lactic_acid SqlType(TEXT) */
    val lacticAcid: Rep[String] = column[String]("lactic_acid")
    /** Database column temp SqlType(TEXT) */
    val temp: Rep[String] = column[String]("temp")

    /** Primary key of Pacdata (database name pacdata_PK) */
    val pk = primaryKey("pacdata_PK", (id, sample))
  }
  /** Collection-like TableQuery object for table Pacdata */
  lazy val Pacdata = new TableQuery(tag => new Pacdata(tag))

  /** Entity class storing rows of table Pcdata
   *  @param id Database column id SqlType(INT), AutoInc
   *  @param sample Database column sample SqlType(VARCHAR), Length(255,true)
   *  @param info Database column info SqlType(TEXT)
   *  @param ethanol Database column ethanol SqlType(TEXT)
   *  @param lactic Database column lactic SqlType(TEXT)
   *  @param sugar Database column sugar SqlType(TEXT)
   *  @param ph Database column ph SqlType(TEXT)
   *  @param acetic Database column acetic SqlType(TEXT)
   *  @param moisture Database column moisture SqlType(TEXT)
   *  @param backup1 Database column backup1 SqlType(TEXT)
   *  @param backup2 Database column backup2 SqlType(TEXT) */
  case class PcdataRow(id: Int, sample: String, info: String, ethanol: String, lactic: String, sugar: String, ph: String, acetic: String, moisture: String, backup1: String, backup2: String)
  /** GetResult implicit for fetching PcdataRow objects using plain SQL queries */
  implicit def GetResultPcdataRow(implicit e0: GR[Int], e1: GR[String]): GR[PcdataRow] = GR{
    prs => import prs._
    PcdataRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table pcdata. Objects of this class serve as prototypes for rows in queries. */
  class Pcdata(_tableTag: Tag) extends profile.api.Table[PcdataRow](_tableTag, Some("brewing_database"), "pcdata") {
    def * = (id, sample, info, ethanol, lactic, sugar, ph, acetic, moisture, backup1, backup2) <> (PcdataRow.tupled, PcdataRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(sample), Rep.Some(info), Rep.Some(ethanol), Rep.Some(lactic), Rep.Some(sugar), Rep.Some(ph), Rep.Some(acetic), Rep.Some(moisture), Rep.Some(backup1), Rep.Some(backup2))).shaped.<>({r=>import r._; _1.map(_=> PcdataRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column sample SqlType(VARCHAR), Length(255,true) */
    val sample: Rep[String] = column[String]("sample", O.Length(255,varying=true))
    /** Database column info SqlType(TEXT) */
    val info: Rep[String] = column[String]("info")
    /** Database column ethanol SqlType(TEXT) */
    val ethanol: Rep[String] = column[String]("ethanol")
    /** Database column lactic SqlType(TEXT) */
    val lactic: Rep[String] = column[String]("lactic")
    /** Database column sugar SqlType(TEXT) */
    val sugar: Rep[String] = column[String]("sugar")
    /** Database column ph SqlType(TEXT) */
    val ph: Rep[String] = column[String]("ph")
    /** Database column acetic SqlType(TEXT) */
    val acetic: Rep[String] = column[String]("acetic")
    /** Database column moisture SqlType(TEXT) */
    val moisture: Rep[String] = column[String]("moisture")
    /** Database column backup1 SqlType(TEXT) */
    val backup1: Rep[String] = column[String]("backup1")
    /** Database column backup2 SqlType(TEXT) */
    val backup2: Rep[String] = column[String]("backup2")

    /** Primary key of Pcdata (database name pcdata_PK) */
    val pk = primaryKey("pcdata_PK", (id, sample))
  }
  /** Collection-like TableQuery object for table Pcdata */
  lazy val Pcdata = new TableQuery(tag => new Pcdata(tag))

  /** Entity class storing rows of table Strain
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param initial Database column initial SqlType(VARCHAR), Length(255,true)
   *  @param speciesType Database column species_type SqlType(TEXT)
   *  @param chinese Database column chinese SqlType(TEXT)
   *  @param latin Database column latin SqlType(TEXT)
   *  @param genus Database column genus SqlType(TEXT)
   *  @param species Database column species SqlType(TEXT)
   *  @param lamCode Database column lam_code SqlType(TEXT)
   *  @param patent Database column patent SqlType(TEXT)
   *  @param patentCode Database column patent_code SqlType(TEXT)
   *  @param source Database column source SqlType(TEXT)
   *  @param apply Database column apply SqlType(TEXT)
   *  @param feature Database column feature SqlType(TEXT)
   *  @param text Database column text SqlType(TEXT)
   *  @param notes Database column notes SqlType(TEXT) */
  case class StrainRow(id: Int, initial: String, speciesType: String, chinese: String, latin: String, genus: String, species: String, lamCode: String, patent: String, patentCode: String, source: String, apply: String, feature: String, text: String, notes: String)
  /** GetResult implicit for fetching StrainRow objects using plain SQL queries */
  implicit def GetResultStrainRow(implicit e0: GR[Int], e1: GR[String]): GR[StrainRow] = GR{
    prs => import prs._
    StrainRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table strain. Objects of this class serve as prototypes for rows in queries. */
  class Strain(_tableTag: Tag) extends profile.api.Table[StrainRow](_tableTag, Some("brewing_database"), "strain") {
    def * = (id, initial, speciesType, chinese, latin, genus, species, lamCode, patent, patentCode, source, apply, feature, text, notes) <> (StrainRow.tupled, StrainRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(initial), Rep.Some(speciesType), Rep.Some(chinese), Rep.Some(latin), Rep.Some(genus), Rep.Some(species), Rep.Some(lamCode), Rep.Some(patent), Rep.Some(patentCode), Rep.Some(source), Rep.Some(apply), Rep.Some(feature), Rep.Some(text), Rep.Some(notes))).shaped.<>({r=>import r._; _1.map(_=> StrainRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get, _12.get, _13.get, _14.get, _15.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column initial SqlType(VARCHAR), Length(255,true) */
    val initial: Rep[String] = column[String]("initial", O.Length(255,varying=true))
    /** Database column species_type SqlType(TEXT) */
    val speciesType: Rep[String] = column[String]("species_type")
    /** Database column chinese SqlType(TEXT) */
    val chinese: Rep[String] = column[String]("chinese")
    /** Database column latin SqlType(TEXT) */
    val latin: Rep[String] = column[String]("latin")
    /** Database column genus SqlType(TEXT) */
    val genus: Rep[String] = column[String]("genus")
    /** Database column species SqlType(TEXT) */
    val species: Rep[String] = column[String]("species")
    /** Database column lam_code SqlType(TEXT) */
    val lamCode: Rep[String] = column[String]("lam_code")
    /** Database column patent SqlType(TEXT) */
    val patent: Rep[String] = column[String]("patent")
    /** Database column patent_code SqlType(TEXT) */
    val patentCode: Rep[String] = column[String]("patent_code")
    /** Database column source SqlType(TEXT) */
    val source: Rep[String] = column[String]("source")
    /** Database column apply SqlType(TEXT) */
    val apply: Rep[String] = column[String]("apply")
    /** Database column feature SqlType(TEXT) */
    val feature: Rep[String] = column[String]("feature")
    /** Database column text SqlType(TEXT) */
    val text: Rep[String] = column[String]("text")
    /** Database column notes SqlType(TEXT) */
    val notes: Rep[String] = column[String]("notes")
  }
  /** Collection-like TableQuery object for table Strain */
  lazy val Strain = new TableQuery(tag => new Strain(tag))

  /** Entity class storing rows of table Straininfo
   *  @param id Database column id SqlType(INT), AutoInc
   *  @param name Database column name SqlType(VARCHAR), Length(255,true)
   *  @param header Database column header SqlType(TEXT)
   *  @param savePlace Database column save_place SqlType(TEXT)
   *  @param saveType Database column save_type SqlType(TEXT)
   *  @param originid Database column originid SqlType(TEXT)
   *  @param chinese Database column chinese SqlType(TEXT)
   *  @param medium Database column medium SqlType(TEXT)
   *  @param position Database column position SqlType(TEXT)
   *  @param source Database column source SqlType(TEXT)
   *  @param apply Database column apply SqlType(TEXT)
   *  @param savePeople Database column save_people SqlType(TEXT)
   *  @param teacher Database column teacher SqlType(TEXT)
   *  @param saveDate Database column save_date SqlType(TEXT)
   *  @param primerSeq Database column primer_seq SqlType(TEXT)
   *  @param `16sSeq` Database column 16s_seq SqlType(TEXT)
   *  @param seqIndex Database column seq_index SqlType(TEXT)
   *  @param emf Database column emf SqlType(TEXT)
   *  @param emfName Database column emf_name SqlType(TEXT)
   *  @param train Database column train SqlType(TEXT)
   *  @param trainName Database column train_name SqlType(TEXT)
   *  @param strainForm Database column strain_form SqlType(TEXT) */
  case class StraininfoRow(id: Int, name: String, header: String, savePlace: String, saveType: String, originid: String, chinese: String, medium: String, position: String, source: String, apply: String, savePeople: String, teacher: String, saveDate: String, primerSeq: String, `16sSeq`: String, seqIndex: String, emf: String, emfName: String, train: String, trainName: String, strainForm: String)
  /** GetResult implicit for fetching StraininfoRow objects using plain SQL queries */
  implicit def GetResultStraininfoRow(implicit e0: GR[Int], e1: GR[String]): GR[StraininfoRow] = GR{
    prs => import prs._
    StraininfoRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table straininfo. Objects of this class serve as prototypes for rows in queries. */
  class Straininfo(_tableTag: Tag) extends profile.api.Table[StraininfoRow](_tableTag, Some("brewing_database"), "straininfo") {
    def * = (id, name, header, savePlace, saveType, originid, chinese, medium, position, source, apply, savePeople, teacher, saveDate, primerSeq, `16sSeq`, seqIndex, emf, emfName, train, trainName, strainForm) <> (StraininfoRow.tupled, StraininfoRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(name), Rep.Some(header), Rep.Some(savePlace), Rep.Some(saveType), Rep.Some(originid), Rep.Some(chinese), Rep.Some(medium), Rep.Some(position), Rep.Some(source), Rep.Some(apply), Rep.Some(savePeople), Rep.Some(teacher), Rep.Some(saveDate), Rep.Some(primerSeq), Rep.Some(`16sSeq`), Rep.Some(seqIndex), Rep.Some(emf), Rep.Some(emfName), Rep.Some(train), Rep.Some(trainName), Rep.Some(strainForm))).shaped.<>({r=>import r._; _1.map(_=> StraininfoRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get, _12.get, _13.get, _14.get, _15.get, _16.get, _17.get, _18.get, _19.get, _20.get, _21.get, _22.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column name SqlType(VARCHAR), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column header SqlType(TEXT) */
    val header: Rep[String] = column[String]("header")
    /** Database column save_place SqlType(TEXT) */
    val savePlace: Rep[String] = column[String]("save_place")
    /** Database column save_type SqlType(TEXT) */
    val saveType: Rep[String] = column[String]("save_type")
    /** Database column originid SqlType(TEXT) */
    val originid: Rep[String] = column[String]("originid")
    /** Database column chinese SqlType(TEXT) */
    val chinese: Rep[String] = column[String]("chinese")
    /** Database column medium SqlType(TEXT) */
    val medium: Rep[String] = column[String]("medium")
    /** Database column position SqlType(TEXT) */
    val position: Rep[String] = column[String]("position")
    /** Database column source SqlType(TEXT) */
    val source: Rep[String] = column[String]("source")
    /** Database column apply SqlType(TEXT) */
    val apply: Rep[String] = column[String]("apply")
    /** Database column save_people SqlType(TEXT) */
    val savePeople: Rep[String] = column[String]("save_people")
    /** Database column teacher SqlType(TEXT) */
    val teacher: Rep[String] = column[String]("teacher")
    /** Database column save_date SqlType(TEXT) */
    val saveDate: Rep[String] = column[String]("save_date")
    /** Database column primer_seq SqlType(TEXT) */
    val primerSeq: Rep[String] = column[String]("primer_seq")
    /** Database column 16s_seq SqlType(TEXT) */
    val `16sSeq`: Rep[String] = column[String]("16s_seq")
    /** Database column seq_index SqlType(TEXT) */
    val seqIndex: Rep[String] = column[String]("seq_index")
    /** Database column emf SqlType(TEXT) */
    val emf: Rep[String] = column[String]("emf")
    /** Database column emf_name SqlType(TEXT) */
    val emfName: Rep[String] = column[String]("emf_name")
    /** Database column train SqlType(TEXT) */
    val train: Rep[String] = column[String]("train")
    /** Database column train_name SqlType(TEXT) */
    val trainName: Rep[String] = column[String]("train_name")
    /** Database column strain_form SqlType(TEXT) */
    val strainForm: Rep[String] = column[String]("strain_form")

    /** Primary key of Straininfo (database name straininfo_PK) */
    val pk = primaryKey("straininfo_PK", (id, name))
  }
  /** Collection-like TableQuery object for table Straininfo */
  lazy val Straininfo = new TableQuery(tag => new Straininfo(tag))

  /** Entity class storing rows of table Strainphe
   *  @param id Database column id SqlType(INT), AutoInc
   *  @param name Database column name SqlType(VARCHAR), Length(255,true)
   *  @param code Database column code SqlType(TEXT)
   *  @param chinese Database column chinese SqlType(TEXT)
   *  @param latin Database column latin SqlType(TEXT)
   *  @param separation Database column separation SqlType(TEXT)
   *  @param sugar Database column sugar SqlType(TEXT)
   *  @param protein Database column protein SqlType(TEXT)
   *  @param tole Database column tole SqlType(TEXT) */
  case class StrainpheRow(id: Int, name: String, code: String, chinese: String, latin: String, separation: String, sugar: String, protein: String, tole: String)
  /** GetResult implicit for fetching StrainpheRow objects using plain SQL queries */
  implicit def GetResultStrainpheRow(implicit e0: GR[Int], e1: GR[String]): GR[StrainpheRow] = GR{
    prs => import prs._
    StrainpheRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table strainphe. Objects of this class serve as prototypes for rows in queries. */
  class Strainphe(_tableTag: Tag) extends profile.api.Table[StrainpheRow](_tableTag, Some("brewing_database"), "strainphe") {
    def * = (id, name, code, chinese, latin, separation, sugar, protein, tole) <> (StrainpheRow.tupled, StrainpheRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(name), Rep.Some(code), Rep.Some(chinese), Rep.Some(latin), Rep.Some(separation), Rep.Some(sugar), Rep.Some(protein), Rep.Some(tole))).shaped.<>({r=>import r._; _1.map(_=> StrainpheRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column name SqlType(VARCHAR), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column code SqlType(TEXT) */
    val code: Rep[String] = column[String]("code")
    /** Database column chinese SqlType(TEXT) */
    val chinese: Rep[String] = column[String]("chinese")
    /** Database column latin SqlType(TEXT) */
    val latin: Rep[String] = column[String]("latin")
    /** Database column separation SqlType(TEXT) */
    val separation: Rep[String] = column[String]("separation")
    /** Database column sugar SqlType(TEXT) */
    val sugar: Rep[String] = column[String]("sugar")
    /** Database column protein SqlType(TEXT) */
    val protein: Rep[String] = column[String]("protein")
    /** Database column tole SqlType(TEXT) */
    val tole: Rep[String] = column[String]("tole")

    /** Primary key of Strainphe (database name strainphe_PK) */
    val pk = primaryKey("strainphe_PK", (id, name))
  }
  /** Collection-like TableQuery object for table Strainphe */
  lazy val Strainphe = new TableQuery(tag => new Strainphe(tag))

  /** Entity class storing rows of table Transcriptome
   *  @param id Database column id SqlType(INT), AutoInc
   *  @param gene Database column gene SqlType(VARCHAR), Length(255,true)
   *  @param flavor Database column flavor SqlType(TEXT)
   *  @param nog Database column nog SqlType(TEXT)
   *  @param function Database column function SqlType(TEXT)
   *  @param category Database column category SqlType(TEXT)
   *  @param ko Database column ko SqlType(TEXT)
   *  @param pathway Database column pathway SqlType(TEXT)
   *  @param enzyme Database column enzyme SqlType(TEXT)
   *  @param modules Database column modules SqlType(TEXT)
   *  @param nr Database column nr SqlType(TEXT)
   *  @param taxonomy Database column taxonomy SqlType(TEXT)
   *  @param trans Database column trans SqlType(TEXT) */
  case class TranscriptomeRow(id: Int, gene: String, flavor: String, nog: String, function: String, category: String, ko: String, pathway: String, enzyme: String, modules: String, nr: String, taxonomy: String, trans: String)
  /** GetResult implicit for fetching TranscriptomeRow objects using plain SQL queries */
  implicit def GetResultTranscriptomeRow(implicit e0: GR[Int], e1: GR[String]): GR[TranscriptomeRow] = GR{
    prs => import prs._
    TranscriptomeRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table transcriptome. Objects of this class serve as prototypes for rows in queries. */
  class Transcriptome(_tableTag: Tag) extends profile.api.Table[TranscriptomeRow](_tableTag, Some("brewing_database"), "transcriptome") {
    def * = (id, gene, flavor, nog, function, category, ko, pathway, enzyme, modules, nr, taxonomy, trans) <> (TranscriptomeRow.tupled, TranscriptomeRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(gene), Rep.Some(flavor), Rep.Some(nog), Rep.Some(function), Rep.Some(category), Rep.Some(ko), Rep.Some(pathway), Rep.Some(enzyme), Rep.Some(modules), Rep.Some(nr), Rep.Some(taxonomy), Rep.Some(trans))).shaped.<>({r=>import r._; _1.map(_=> TranscriptomeRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get, _12.get, _13.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column gene SqlType(VARCHAR), Length(255,true) */
    val gene: Rep[String] = column[String]("gene", O.Length(255,varying=true))
    /** Database column flavor SqlType(TEXT) */
    val flavor: Rep[String] = column[String]("flavor")
    /** Database column nog SqlType(TEXT) */
    val nog: Rep[String] = column[String]("nog")
    /** Database column function SqlType(TEXT) */
    val function: Rep[String] = column[String]("function")
    /** Database column category SqlType(TEXT) */
    val category: Rep[String] = column[String]("category")
    /** Database column ko SqlType(TEXT) */
    val ko: Rep[String] = column[String]("ko")
    /** Database column pathway SqlType(TEXT) */
    val pathway: Rep[String] = column[String]("pathway")
    /** Database column enzyme SqlType(TEXT) */
    val enzyme: Rep[String] = column[String]("enzyme")
    /** Database column modules SqlType(TEXT) */
    val modules: Rep[String] = column[String]("modules")
    /** Database column nr SqlType(TEXT) */
    val nr: Rep[String] = column[String]("nr")
    /** Database column taxonomy SqlType(TEXT) */
    val taxonomy: Rep[String] = column[String]("taxonomy")
    /** Database column trans SqlType(TEXT) */
    val trans: Rep[String] = column[String]("trans")

    /** Primary key of Transcriptome (database name transcriptome_PK) */
    val pk = primaryKey("transcriptome_PK", (id, gene))
  }
  /** Collection-like TableQuery object for table Transcriptome */
  lazy val Transcriptome = new TableQuery(tag => new Transcriptome(tag))
}
