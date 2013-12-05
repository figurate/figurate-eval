import javax.swing.JTable
import java.awt.Image
import java.awt.image.ImageProducer

class Evaluation {

    def input
    def result

    String toString() {
        if (result) {
            if (result instanceof Exception) {
                return "<html><p>${input}</p><p style='font-style:italic;color:silver'>Resulted in exception: ${result}</p></html>"
            }
            else if (result instanceof Closure) {
                return "<html><p>${input}</p><p style='font-style:italic;color:silver'>Resulted in closure</p></html>"
            }
            else if (result instanceof ImageProducer || result instanceof Image) {
                return "<html><p>${input}</p><p style='font-style:italic;color:silver'>Resulted in image</p></html>"
            }
            else if (result instanceof JTable) {
                return "<html><p>${input}</p><p style='font-style:italic;color:silver'>Resulted in table</p></html>"
            }
            else {
                return "<html><p>${input}</p><p> = ${result}</p></html>"
            }
        }
        else {
            return "<html><p>${input}</p><p style='font-style:italic;color:silver'>No Result</p></html>"
        }
    }
}
