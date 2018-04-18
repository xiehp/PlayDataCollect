package xie.playdatacollect.core.dictionary;

import xie.framework.core.service.dictionary.utils.PublicDictionaryLoader;

public class PlaydataPublicDictionaryLoader extends PublicDictionaryLoader {

	public static String getPlaydataValue(String code) {
		return getValue(PlaydataPublicDictionaryConst.TYPE_PLAY_DATA,   code);
	}
}
