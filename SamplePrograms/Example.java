class Example {
	public static void main( String []args ){
		{
			a = b;
			a = b;
			b = a + (foo + bar) + baz;
			System.out.println( baz);
			a = true;
			a = false;
			a = ((b));
			a = 123 - b;
			b = 12 - 11111;
			a = a && b;
			a = a < b;
			a = a[12];
			a = a.length;
			a = this;
			a = new int [1];
			a = a.method(a);
			a = a.method2(1+1);
			a = b.method3(1,2);
			m = new Clase();
			z = !a;
			z = !true;
			a = (12);
	
			if ( true ) a = 1; else a = 0;
			while ( a < 10 ) a = a + 1;
			System.out.println( 1234567890 );
			a = 1;
			
			// comentario simple
			/* comentario simple2 */
			/** comentario javadocs */
			/*
			 * comentario multilinea
			 * linea2
			 * */
			arreglo[1]=30;
			{
				a = b;
			    b = a;
			}
		}
	}
}