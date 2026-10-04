package app.estudiante.utils.seguridad;


import at.favre.lib.crypto.bcrypt.BCrypt;

public final class BcryptPasswordHasher implements PasswordHasher {

    private final int cost;

    public BcryptPasswordHasher(int cost) {
        this.cost = cost;
    }

    @Override
    public String hash(char[] password) {
        try {
            return BCrypt.withDefaults().hashToString(cost, password);
        } finally {
            SecurityUtils.wipe(password);
        }
    }

    @Override
    public boolean verify(char[] password, String storedHash) {
        try {
            BCrypt.Result res = BCrypt.verifyer().verify(password, storedHash);
            return res.verified;
        } finally {
            SecurityUtils.wipe(password);
        }
    }

    @Override
    public boolean needsRehash(String storedHash) {
        int currentCost = SecurityUtils.extractBcryptCost(storedHash);
        return currentCost < this.cost;
    }

}
