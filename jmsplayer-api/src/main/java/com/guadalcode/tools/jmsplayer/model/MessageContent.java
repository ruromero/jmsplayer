package com.guadalcode.tools.jmsplayer.model;

public class MessageContent {

    private final String type;
    private final String text;

    public MessageContent(String text, String type) {
	this.text = text;
	this.type = type;
    }

    public MessageContent(String text) {
	this.text = text;
	type = null;
    }

    public String getType() {
	return type;
    }

    public String getText() {
	return text;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((text == null) ? 0 : text.hashCode());
	result = prime * result + ((type == null) ? 0 : type.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	MessageContent other = (MessageContent) obj;
	if (text == null) {
	    if (other.text != null)
		return false;
	} else if (!text.equals(other.text))
	    return false;
	if (type == null) {
	    if (other.type != null)
		return false;
	} else if (!type.equals(other.type))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "MessageContent [type=" + type + ", text=" + text + "]";
    }
    
}
