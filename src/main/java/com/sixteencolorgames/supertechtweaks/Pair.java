/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks;

/**
 *
 * @author oa10712
 */
public class Pair<E, F> {

	private E left;
	private F right;

	public Pair(E o1, F o2) {
		left = o1;
		right = o2;
	}

	public E getLeft() {
		return left;
	}

	public F getRight() {
		return right;
	}
}
