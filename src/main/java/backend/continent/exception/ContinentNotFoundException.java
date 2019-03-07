package backend.continent.exception;

public class ContinentNotFoundException extends NotFoundException{
        public ContinentNotFoundException(String name) {
            super("Could not find continent " + name);
        }
}
