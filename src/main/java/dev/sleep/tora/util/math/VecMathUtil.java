package dev.sleep.tora.util.math;

/**
 * Utility vecmath class used when computing the hash code for vecmath objects
 * containing float or double values.
 */
class VecMathUtil {
	
	/**
	 * Do not construct an instance of this class.
	 */
	private VecMathUtil() {
	}

	static final long hashLongBits(long hash, long l) {
		hash *= 31L;
		return hash + l;
	}

	static final long hashFloatBits(long hash, float f) {
		hash *= 31L;
		// Treat 0.0d and -0.0d the same (all zero bits)
		if (f == 0.0f)
			return hash;

		return hash + Float.floatToIntBits(f);
	}

	static final long hashDoubleBits(long hash, double d) {
		hash *= 31L;
		// Treat 0.0d and -0.0d the same (all zero bits)
		if (d == 0.0d)
			return hash;

		return hash + Double.doubleToLongBits(d);
	}

	/**
	 * Return an integer hash from a long by mixing it with itself.
	 */
	static final int hashFinish(long hash) {
		return (int) (hash ^ (hash >> 32));
	}
}
