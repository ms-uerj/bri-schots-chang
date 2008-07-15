package br.ufjf.cos.disciplina.bri.indexing;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import br.ufjf.cos.disciplina.bri.indexing.model.RadixInfo;
import br.ufrj.cos.disciplina.bri.model.Record;

public class Indexing {
	
	/*
	 * The hash will be composed as follows:
	 * The radix will be the key of the hash
	 * The associated element will be a list of RadixInfo,
	 * where RadixInfo objects contains information about
	 * the document where a radix occurs and how much times
	 * this radix occurs
	 */
	private Hashtable<String, List<RadixInfo>> hash;
	
	/**
	 * Constructor method
	 */
	public Indexing() {
		hash = new Hashtable<String, List<RadixInfo>>();
	}
	
	/**
	 * Adds a certain radix and its information
	 * @param radix - the radix to be added
	 * @param info - the information about the radix
	 */
	public void addToHash(String radix, RadixInfo info) {
		if (hash.containsKey(radix)) {
			// hash key already exists, just add the information to the list
			(hash.get(radix)).add(info);		
		} else {
			// create the list, add the information and put it on the hash
			List<RadixInfo> list = new Vector<RadixInfo>();
			list.add(info);
			hash.put(radix, list);
		}
	}
	

}
