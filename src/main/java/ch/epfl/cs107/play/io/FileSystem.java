package ch.epfl.cs107.play.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Represents a simple file system, where each file is associated to a unique name.
 */
public interface FileSystem {
    
    /**
     * Open an existing file for read.
     * @param name unique identifier, not null
     * @return content stream, not null
     * @throws IOException if file cannot be open for read
     */
    public InputStream read(String name) throws IOException;
    
    /**
     * Open file for write, previous content overwritten if any.
     * @param name unique identifier, not null
     * @return content stream, not null
     * @throws IOException if file cannot be open for write
     */
    public OutputStream write(String name) throws IOException;
    
}
