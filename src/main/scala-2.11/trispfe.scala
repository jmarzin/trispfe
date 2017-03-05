import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser._

/**
  * Created by jacquesmarzin on 04/03/2017.
  */
object trispfe extends App {
  val reader = new PdfReader("F4.pdf")
  val parser = new PdfReaderContentParser(reader)
  val rect = new Rectangle(194f,504f,256f,544f)
  val filter = new RegionTextRenderFilter(rect)
  for (i <- 1 to reader.getNumberOfPages()) {
    val texte = PdfTextExtractor.getTextFromPage(reader, i)
    if (texte.contains("Facture") && texte.contains("Référence")) {
      var strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter)
      val texte2 = PdfTextExtractor.getTextFromPage(reader, i, strategy)
      println(texte2)
      val modele = """(?s).*?(\d+) *(\D) *(\d+).*""".r
      var aGarder = false
      texte2 match {
        case modele(annee,lettre,numero) => if(List("P", "D", "F", "S", "U", "V").contains(lettre)) {
            aGarder = true
            println("je garde")
          } else {
            aGarder = false
            println("je ne garde pas")
          }
      }
    }
  }
  println("coucou")
}
