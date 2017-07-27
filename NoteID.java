public class NoteID {

	/**
	 *Determine whether correct note is played
	 *@param f The target frequency(Hz)
	 *@param b The byte array to be analyzed
	 *@param s The sample rate of the byte array
	 */
	public void noteID (double f, byte[] b, double s) {
		int size = (int)(s/f);
		byte[] x = new byte[size];
		
		int a = 0; //Used to reset position in x
		for (int i = 0; i < b.length; i++) {
			if (a >= x.length)
				a = 0;
			x[a] += b[i];
			a++;
		}
		int max = (int)x[0];
		int min = (int)x[0];
		for (int i = 0; i < x.length; i++) {
			max = Math.max(max, x[i]);
			min = Math.min(min, x[i]);
		}
		System.out.print(max - min + " ");
	}
}