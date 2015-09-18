package com.kpinfotech.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.kpinfotech.model.RssItem;

import android.util.Xml;

public class RssParser {

	public ArrayList<RssItem> parse(InputStream inputStream) throws XmlPullParserException, IOException {
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(inputStream, null);
			parser.nextTag();
			return readFeed(parser);
		} finally {
			inputStream.close();
		}
	}

	private ArrayList<RssItem> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "rss");
		
		boolean insideItem = false;
		int eventType = parser.getEventType();
		
		String title = null;
		String thumbnail = null;
		String summary = null;
		String audio = null;
		String duration = null;
		
		ArrayList<RssItem> items = new ArrayList<RssItem>();
		
		while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {

            	String name = parser.getName();
                if (name.equalsIgnoreCase("item")) {
                    insideItem = true;
                }
                else if (name.equalsIgnoreCase("title")) {
                    if (insideItem) {
                    	title = parser.nextText();
                    }
                } 
                else if (name.equalsIgnoreCase("itunes:image")) {
                    if (insideItem) {
                    	thumbnail = parser.getAttributeValue(null, "href");
                    }
                }
                else if (name.equalsIgnoreCase("itunes:summary")) {
                    if (insideItem) {
                    	summary = parser.nextText();
                    }
                }
                else if (name.equalsIgnoreCase("enclosure")) {
                    if (insideItem) {
                    	audio = parser.getAttributeValue(null, "url");
                    }
                }
                else if (name.equalsIgnoreCase("itunes:duration")) {
                    if (insideItem) {
                    	duration = parser.nextText();
                    }
                }
                
                if ((title != null && audio != null && thumbnail != null && summary != null && duration != null) || (title != null && audio != null)) {
                	
    				RssItem item = new RssItem(title, thumbnail, summary, audio, duration);
    				items.add(item);
    				
    				title = null;
    				thumbnail = null;
    				summary = null;
    				audio = null;
    				duration = null;
    			}

            } else if (eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("item")) {
                insideItem = false;
            }
            
            eventType = parser.next(); /// move to next element
        }
		
		return items;
	}

}