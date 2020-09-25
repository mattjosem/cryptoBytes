/**
 * 
 */
package Application;

import java.math.BigInteger;

/**
 * @author jr-99
 *
 */
public class EllipticCurve implements Comparable<EllipticCurve> {
	BigInteger mersennePrime;
	BigInteger E521;
	final private static BigInteger d = BigInteger.valueOf(Long.parseLong("-376014"));
	BigInteger point[] = {BigInteger.valueOf((long) 4), BigInteger.valueOf((long) 10)};
	EllipticCurve() {
		
	}
	EllipticCurve(BigInteger x, BigInteger y) {
		
	}
	EllipticCurve(BigInteger x, BigInteger leastBit, String s) {
		
	}
	public BigInteger[] oppositePoint() {
		BigInteger opposite[] = new BigInteger[2];
		opposite[0] = point[0].multiply(BigInteger.valueOf(-1));
		opposite[1] = point[1];
		return opposite;
		
	}
	public BigInteger[] sumPoints(BigInteger[] ten) {
		return ten;
	}

	/**  * Compute a square root of v mod p with a specified  
	 * * least significant bit, if such a root exists.  
	 * *  * @param   v   the radicand.  
	 * * @param   p   the modulus (must satisfy p mod 4 = 3).  
	 * * @param   lsb desired least significant bit (true: 1, false: 0).  
	 * * @return  a square root r of v mod p with r mod 2 = 1 iff lsb = true  
	 *   *          if such a root exists, otherwise null.  */ 
	
	
	public static BigInteger sqrt(BigInteger v, BigInteger p, boolean lsb) {     
		assert (p.testBit(0) && p.testBit(1)); // p = 3 (mod 4)     
		if (v.signum() == 0) {         
			return BigInteger.ZERO;     
		}     
		BigInteger r = v.modPow(p.shiftRight(2).add(BigInteger.ONE), p);     
		if (r.testBit(0) != lsb) {         
			r = p.subtract(r); // correct the lsb     
		}     
		return (r.multiply(r).subtract(v).mod(p).signum() == 0) ? r : null; 
		}
	@Override
	public int compareTo(EllipticCurve o) {
		int same = -1;
		if(this.point[0].compareTo(o.point[0]) == 0 && this.point[1].compareTo(o.point[1]) == 0) {
			same = 0;
		}
		// TODO Auto-generated method stub
		return same;
	} 
		
}
	 

