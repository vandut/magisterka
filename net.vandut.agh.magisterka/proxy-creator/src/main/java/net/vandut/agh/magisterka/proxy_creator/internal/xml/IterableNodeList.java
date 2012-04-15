package net.vandut.agh.magisterka.proxy_creator.internal.xml;

/*
 * Copyright (C) 2011 Konrad Bielak
 * Zabronione rozpowszechnianie tego pliku bez zgody autora
 */

import java.util.Iterator;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class IterableNodeList implements NodeList, Iterable<Node> {
	private final NodeList nodeList;

	public IterableNodeList(NodeList nodeList) {
		this.nodeList = nodeList;
	}

	@Override
	public int getLength() {
		return nodeList.getLength();
	}

	@Override
	public Node item(int index) {
		return nodeList.item(index);
	}

	@Override
	public Iterator<Node> iterator() {
		return new NodeListIterator(this);
	}
}

class NodeListIterator implements Iterator<Node> {
	private final IterableNodeList iterableNodeList;
	private int position = 0;

	public NodeListIterator(IterableNodeList iterableNodeList) {
		this.iterableNodeList = iterableNodeList;
	}

	@Override
	public boolean hasNext() {
		return position < iterableNodeList.getLength();
	}

	@Override
	public Node next() {
		return iterableNodeList.item(position++);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("NodeList may not be changed");
	}
}
