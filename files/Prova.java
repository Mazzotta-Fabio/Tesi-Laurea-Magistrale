	private static String parse(char []buf){
		//dichirazione indici
    	int i=0;
		//inizializzo etichette
		String etichetta ="LS";
		//creiamo i nodi del gss inserendo un arco tra u0 e u1
		Vertex<String> u0=gss.insertVertex("$");
		gss.insertVertex("Ls0L0");
		gss.insertDirectedEdge(gss.getLastNode(),u0, "");
		Vertex<String> cu=gss.getLastNode();
		//creazione nodo sppf
		Vertex<IdNodoSppf> cn=sppf.insertVertex(new IdNodoSppf("S","S"));
		cn.element().setId(cn.hashCode());
		ControllerNode.setFirstInt(cn.element().getId());
		while(true){
			switch(etichetta){
			//non terminale LL1
			//S
			case "LS":
				if(test(buf[i],"S","ASd")){add("LS1",cu,i,cn);}
				if(test(buf[i],"S","BS")){add("LS2",cu,i,cn);}
				if(test(buf[i],"S","epsilon")) {add("LS3",cu,i,cn);}
				etichetta="L0";
				break;
			//terminale LL1 A
			case "LA":
				if(test(buf[i],"A","a")){etichetta="La";}
				if(test(buf[i],"A","c")){etichetta="Lc";}
				break;
			//terminale LL1 B
			case "LB":
				if(test(buf[i],"B","a")){etichetta="Lab";}
				if(test(buf[i],"B","b")){etichetta="Lb";}
				break;
			//.ASd
			case "LS1":
				if(test(buf[i],"S","ASd")){
					cu=create("L1",cu,i,cn);
					cn=getNodeT("A","S->*ASd",cn);
					etichetta="LA";
				}
				else{
					etichetta="L0";
				}
				break;
			//A.Sd
			case "L1":
				if(test(buf[i],"S","Sd")){
					cu=create("L2",cu,i,cn);
					cn=getNodeP(cn);
					cn=getNodeT("S","S->A*Sd",cn);
					etichetta="LS";
				}
				else{
					etichetta="L0";}
				break;
			//AS.d
			case "L2":
				if(buf[i]=='d'){
					i++;cn=getNodeP(cn);
					cn=getNodeT("d","S->AS*d",cn);
					etichetta="Ld";
				}
				else{
					etichetta="L0";}
				break;
			//ASd.
			case "Ld":
				cn=getNodeP(cn);
				pop(cu,i,u0,cn);
				etichetta="L0";
				break;
			//.BS
			case "LS2":
				op.add(OperazioneLineare.creaStato(etichetta,"S->*BS"));
				if(test(buf[i],"S","BS")){
					cu=create("L3",cu,i,cn);
					cn=getNodeT("B","S->*BS",cn);
					etichetta="LB";}
				else{
					etichetta="L0";}
				break;
			//B.S
			case "L3":
				op.add(OperazioneLineare.creaStato(etichetta,"S->B*S"));
				if(test(buf[i],"S","S")){
					cu=create("L4",cu,i,cn);
					cn=getNodeP(cn);
					cn=getNodeT("S","S->B*S",cn);
					etichetta="LS";
				}
				else{
					etichetta="L0";}
				break;
			//BS.
			case "L4":
				cn=getNodeP(cn);
				pop(cu,i,u0,cn);
				etichetta="L0";
				break;
			//epsilon
			case "LS3":
				cn=getNodeT("e","S->*e",cn);
				cn=getNodeP(cn);
				pop(cu,i,u0,cn);
				etichetta="L0";
				break;
			//.a
			case "La":
				if(buf[i]=='a'){
					cn=getNodeT("a","A->*a",cn);
					i++;etichetta="La1";}
				else{
					etichetta="L0";}
				break;
			//a.
			case "La1":
				cn=getNodeP(cn);
				pop(cu,i,u0,cn);
				etichetta="L0";
				break;
			//.a
			case "Lab":
				if(buf[i]=='a'){
					cn=getNodeT("a","B->*a",cn);
					i++;etichetta="La1b";
				}
				else{
					etichetta="L0";}
				break;
			//a.
			case "La1b":
				cn=getNodeP(cn);
				pop(cu,i,u0,cn);
				etichetta="L0";
				break;
			//.b
			case "Lb":
				op.add(OperazioneLineare.creaStato(etichetta,"B->*b"));
				if(buf[i]=='b'){
					cn=getNodeT("b","B->*b",cn);
					i++;etichetta="Lb1";
				}
				else{
					etichetta="L0";
				}
				break;
			//b.
			case "Lb1":
				cn=getNodeP(cn);
				pop(cu,i,u0,cn);
				etichetta="L0";
				break;
			//.c
			case "Lc":
				op.add(OperazioneLineare.creaStato(etichetta,"A->*c"));
				if(buf[i]=='c'){
					cn=getNodeT("c","A->*c",cn);
					i++;etichetta="Lc1";
				}
				else{
					etichetta="L0";}
				break;
			//c.
			case "Lc1":
				cn=getNodeP(cn);
				pop(cu,i,u0,cn);
				etichetta="L0";
				break;
			case "L0":
				if(r.size()>0){
					etichetta=r.get(0).getEtichetta();
					i=r.get(0).getI();
					cu=r.get(0).getU();
					cn=r.get(0).getW();
					System.out.println(r.get(0));
					r.remove(0);
				}
				else{
					if(u.size()==0) {return "NON SUCCESSO";}
					else{
						if((u.get(u.size()-1).getEtichetta().equals("L0"))&&(u.get(u.size()-1).getU().element().equals(u0.element()))){
							return "SUCCESSO";
						}
						else{
							return "NON SUCCESSO";
						}
					}
				}
				break;
			}
		}
	}
	