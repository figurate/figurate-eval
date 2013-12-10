import groovy.xml.MarkupBuilder

import javax.swing.JTable
import java.awt.Image
import java.awt.image.ImageProducer

class Evaluation {

    def input
    def result

    String toString() {
        MarkupBuilder html = []
        if (result) {
            if (result instanceof Exception) {
                html.html {
                    p(input)
                    p(style: 'font-style:italic;color:silver', "Resulted in exception: ${result}")
                }
            }
            else if (result instanceof Closure) {
                html.html {
                    p(input)
                    p(style: 'font-style:italic;color:silver', "Resulted in closure: ${result}")
                }
            }
            else if (result instanceof ImageProducer || result instanceof Image) {
                html.html {
                    p(input)
                    p(style: 'font-style:italic;color:silver', "Resulted in image: ${result}")
                }
            }
            else if (result instanceof JTable) {
                html.html {
                    p(input)
                    p(style: 'font-style:italic;color:silver', "Resulted in table: ${result}")
                }
            }
            else {
                html.html {
                    p(input)
                    p(" = ${result}")
                }
            }
        }
        else {
            html.html {
                p(input)
                p('No Result')
            }
        }
    }
}
