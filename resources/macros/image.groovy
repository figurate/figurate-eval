import javax.imageio.ImageIO

robot = [
    description: 'Create a new robot image from http://robohash.org using the specified text.',
    expression: { text, imageType = 'png' -> ImageIO.read(new URL("http://robohash.org/${text}.${imageType}")) }
]

save = [
    description: 'Save a RenderableImage to a file.',
    expression: { image, String filename, imageType = filename.find ~/(?<=\.)[0-9a-z]+$/ ->
        ImageIO.write(image, imageType, new File(filename))
    }
]
