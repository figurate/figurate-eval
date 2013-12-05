import javax.imageio.ImageIO
import javax.swing.ImageIcon
import java.awt.Toolkit

image = [
    robot: { text, imageType = 'png' ->
//        def data = new URL("http://robohash.org/${text}.${imageType}").content
//        new ImageIcon(Toolkit.defaultToolkit.createImage(data)).image
//        Toolkit.defaultToolkit.createImage(data)
        ImageIO.read(new URL("http://robohash.org/${text}.${imageType}"))
    },

    save: { image, String filename, imageType = filename.find ~/(?<=\.)[0-9a-z]+$/ -> ImageIO.write(image, imageType, new File(filename)) }
]

file = [
    save: { byte[] data, filename -> new File(filename).withOutputStream { out ->
          out << data
    }},

//    saveImage: { image, imageType, filename -> ImageIO.write(image, imageType, new File(filename)) }
]