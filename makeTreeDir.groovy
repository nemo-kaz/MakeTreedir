@Grab('org.apache.commons:commons-lang3:3.12.0')
import org.apache.commons.lang3.StringUtils
import java.nio.file.*
import java.nio.file.attribute.*

def outputFile = new File('directory_tree.txt')

// Delete the file if it exists to avoid file locking issues
if (outputFile.exists()) {
    outputFile.delete()
    Thread.sleep(100) // Give the system time to release the file handle
}

outputFile.withWriter('UTF-8') { writer ->
    def rootPath = Paths.get('C:\\')
    processDirectory(rootPath, writer, rootPath.toString())
}

def processDirectory(Path dir, Writer writer, String rootPath) {
    try {
        // Skip system and special directories
        def dirName = dir.fileName?.toString() ?: ''
        def dirPath = dir.toString()
        
        // Check if directory should be skipped
        def skipReason = getSkipReason(dirPath)
        if (skipReason) {
            println "Skipped: ${dirPath} (${skipReason})"
            return
        }
        
        // Write the relative path (remove the root path part)
        def relativePath = dirPath.substring(rootPath.size() - 1).replace('\\', '/')
        writer.write("$relativePath\n")
        
        // Process subdirectories
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            stream.each { path ->
                if (Files.isDirectory(path)) {
                    processDirectory(path, writer, rootPath)
                }
            }
        } catch (Exception e) {
            // Skip directories we can't access
            println "Skipped: ${dirPath} (Access denied)"
        }
    } catch (Exception e) {
        // Continue with next directory on any error
    }
}

def getSkipReason(String path) {
    // Convert to lowercase for case-insensitive matching
    def lowerPath = path.toLowerCase()
    
    // Check for subdirectories starting with special characters (_, $, ., ^, ~, `)
    // Split path into components and check if any starts with these characters
    def pathComponents = path.split(/[\\/]/)
    for (component in pathComponents) {
        if (component && (component.startsWith('_') || component.startsWith('$') || 
            component.startsWith('.') || component.startsWith('^') || 
            component.startsWith('~') || component.startsWith('`'))) {
            return "Special character at start: ${component}"
        }
    }
    
    // Check for excluded directory names anywhere in the path
    def excludedNames = ['appdata', 'diffbrowser', 'winsxs', 'system volume information', 
                         'programdata', 'cryptoator']
    for (excluded in excludedNames) {
        if (lowerPath.contains(excluded)) {
            return "Contains excluded name: ${excluded}"
        }
    }
    
    // Check for encoding issues (non-printable or invalid characters)
    def pathWithoutSlashes = path.replaceAll(/[\\/]/, '')
    if (!StringUtils.isAsciiPrintable(pathWithoutSlashes)) {
        return "Encoding issue"
    }
    
    // Check if directory is readable
    try {
        if (!Files.isReadable(Paths.get(path))) {
            return "Not readable"
        }
    } catch (Exception e) {
        return "Access error"
    }
    
    return null
}

println "Directory tree has been written to: ${outputFile.absolutePath}"
