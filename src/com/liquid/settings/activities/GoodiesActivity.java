/*
** Copyright (C) 2011 The Liquid Settings Project
**
** Licensed under the Apache License, Version 2.0 (the "License"); 
** you may not use this file except in compliance with the License. 
** You may obtain a copy of the License at 
**
**     http://www.apache.org/licenses/LICENSE-2.0 
**
** Unless required by applicable law or agreed to in writing, software 
** distributed under the License is distributed on an "AS IS" BASIS, 
** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
** See the License for the specific language governing permissions and 
** limitations under the License.
*/

package com.liquid.settings.activities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StatFs;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.text.format.Formatter;

import com.liquid.settings.R;

public class GoodiesActivity extends PreferenceActivity {

	// Does
	private static final String DOESGINGY_COMMAND = "DoesGingy_Command";
	private static final String DOESGREEN_COMMAND = "DoesGreen_Command";
	private static final String DOESLIGHTBLUE_COMMAND = "DoesLightBlue_Command";
	private static final String DOESMEDBLUE_COMMAND = "DoesMedBlue_Command";
	private static final String DOESORANGE_COMMAND = "DoesOrange_Command";
	private static final String DOESPINK_COMMAND = "DoesPink_Command";
	private static final String DOESPURPLE_COMMAND = "DoesPurple_Command";
	private static final String DOESRED_COMMAND = "DoesRed_Command";
	private static final String DOESSMOKED_COMMAND = "DoesSmoked_Command";
	private static final String DOESTWOBLUE_COMMAND = "DoesTwoBlue_Command";
	private static final String DOESWHITE_COMMAND = "DoesWhite_Command";
	private static final String DOESYELLOW_COMMAND = "DoesYellow_Command";

	// Eyes
	private static final String EYESBLUEGREEN_COMMAND = "EyesBlueGreen_Command";
	private static final String EYESCYAN_COMMAND = "EyesCyan_Command";
	private static final String EYESGREEN_COMMAND = "EyesGreen_Command";
	private static final String EYESLIGHTBLUE_COMMAND = "EyesLightBlue_Command";
	private static final String EYESORANGE_COMMAND = "EyesOrange_Command";
	private static final String EYESPINK_COMMAND = "EyesPink_Command";
	private static final String EYESPURPLE_COMMAND = "EyesPurple_Command";
	private static final String EYESRED_COMMAND = "EyesRed_Command";
	private static final String EYESYELLOW_COMMAND = "EyesYellow_Command";

	// Gingy
	private static final String GINGYFASTER_COMMAND = "GingyFaster_Command";
	private static final String GINGYSLOWER_COMMAND = "GingySlower_Command";

	// Halloween
	private static final String HALLOWSCARY_COMMAND = "HallowScary_Command";
	private static final String HALLOWSKULL_COMMAND = "HallowSkull_Command";
	private static final String HALLOWSPIRAL_COMMAND = "HallowSpiral_Command";

	// Liquid
	private static final String LIQUIDDEFAULT_COMMAND = "LiquidDefault_Command";
	private static final String LIQUIDEARTH_COMMAND = "LiquidEarth_Command";
	private static final String LIQUIDROTATE_COMMAND = "LiquidRotate_Command";
	private static final String LIQUIDSMOOTH_COMMAND = "LiquidSmooth_Command";
	private static final String LIQUIDSPARKLE_COMMAND = "LiquidSparkle_Command";
	private static final String LIQUIDSPLASH_COMMAND = "LiquidSplash_Command";
	
	// Other
	private static final String ANDROIDFROG_COMMAND = "AndroidFrog_Command";
	private static final String ANGRYBIRDS_COMMAND = "AngryBirds_Command";
	private static final String APPLEOUCH_COMMAND = "AppleOuch_Command";
	private static final String ARCREACTOR_COMMAND = "ArcReactor_Command";
	private static final String COLORTBOLT_COMMAND = "ColorTbolt_Command";
	private static final String CUPOFWATER_COMMAND = "CupOfWater_Command";
	private static final String DARKBLUELFY_COMMAND = "DarkBlueLfy_Command";
	private static final String INMEMORYOF_COMMAND = "InMemoryOf_Command";
	private static final String MERRYXMAS_COMMAND = "MerryXmas_Command";
	private static final String ROSEBLOOM_COMMAND = "RoseBloom_Command";
	private static final String SIMPLYSTUNNING_COMMAND = "SimplyStunning_Command";
	private static final String SOLARSYSTEM_COMMAND = "SolarSystem_Command";
	
    // Radial
	private static final String RADIALDARKBLUE_COMMAND = "RadialDarkBlue_Command";
	private static final String RADIALDEFAULT_COMMAND = "RadialDefault_Command";
	private static final String RADIALGREEN_COMMAND = "RadialGreen_Command";
	private static final String RADIALLIGHTBLUE_COMMAND = "RadialLightBlue_Command";
	private static final String RADIALLIMEGREEN_COMMAND = "RadialLimeGreen_Command";
	private static final String RADIALORANGE_COMMAND = "RadialOrange_Command";
	private static final String RADIALPINK_COMMAND = "RadialPink_Command";
	private static final String RADIALPURPLE_COMMAND = "RadialPurple_Command";
	private static final String RADIALRED_COMMAND = "RadialRed_Command";
	private static final String RADIALSILVER_COMMAND = "RadialSilver_Command";
	private static final String RADIALWHITE_COMMAND = "RadialWhite_Command";
	private static final String RADIALYELLOW_COMMAND = "RadialYellow_Command";
	
	// Sexy
	private static final String SEXYBOOBY_COMMAND = "SexyBooby_Command";
	private static final String SEXYBOOTY_COMMAND = "SexyBooty_Command";

	// Switch
	private static final String STOCKCAMERA_COMMAND = "StockCamera_Command";
    private static final String OTHERCAMERA_COMMAND = "OtherCamera_Command";
	private static final String STOCKCONTACTS_COMMAND = "StockContacts_Command";
    private static final String THEMECONTACTS_COMMAND = "ThemeContacts_Command";
    private static final String STOCKGMAIL_COMMAND = "StockGmail_Command";
    private static final String THEMEGMAIL_COMMAND = "ThemeGmail_Command";
    private static final String ADWLAUNCHER_COMMAND = "ADWLauncher_Command";
    private static final String GOLAUNCHER_COMMAND = "GoLauncher_Command";
	private static final String LAUNCHERPRO_COMMAND = "LauncherPro_Command";
	private static final String STOCKLAUNCHER_COMMAND = "StockLauncher_Command";
    private static final String STOCKMESSAGING_COMMAND = "StockMessaging_Command";
    private static final String OTHERMESSAGING_COMMAND = "OtherMessaging_Command";
	private static final String STOCKMUSIC_COMMAND = "StockMusic_Command";
	private static final String OTHERMUSIC_COMMAND = "OtherMusic_Command";
    private static final String STOCKSTATUSBAR_COMMAND = "StockStatusBar_Command";
    private static final String THEMESTATUSBAR_COMMAND = "ThemeStatusBar_Command";

	// System
	private static final String MOUNTRW_COMMAND = "MountRw_Command";
	private static final String MOUNTRO_COMMAND = "MountRo_Command";
	private static final String ENABLEBOOT_COMMAND = "EnableBoot_Command";
	private static final String SOUNDOFF_COMMAND = "SoundOff_Command";
	private static final String SOUNDON_COMMAND = "SoundOn_Command";
	private static final String FIXPERMS_COMMAND = "FixPerms_Command";
	private static final String CHUCKNORRIS_COMMAND = "ChuckNorris_Command";
	private static final String HOTREBOOT_COMMAND = "HotReboot_Command";
	private static final String CLEARCACHE_COMMAND = "ClearCache_Command";
    private static final String CLEARDALVIK_COMMAND = "ClearDalvik_Command";
    private static final String CLEARSTATS_COMMAND = "ClearStats_Command";
	private static final String DISABLEBOOT_COMMAND = "DisableBoot_Command";
	private static final String ZIPALIGN_COMMAND = "ZipAlign_Command";
	private static final String BLOCKADS_COMMAND = "BlockAds_Command";
	private static final String SHOWADS_COMMAND = "ShowAds_Command";

	// Battery
	private static final String BATTERY_HISTORY = "battery_history_settings";
	private static final String BATTERY_STATUS = "battery_status_settings";
	private static final String USAGE_STATISTICS = "usage_statistics_settings";

	// Partitions
	private static final String SYSTEM_PART_SIZE = "system_storage";
	private Preference mSystemSize;
	private static final String SYSTEM_STORAGE_PATH = "/system";

	private static final String DATA_PART_SIZE = "data_storage";
	private Preference mDataSize;
	private static final String DATA_STORAGE_PATH = "/data";

	private static final String CACHE_PART_SIZE = "cache_storage";
	private Preference mCacheSize;
	private static final String CACHE_STORAGE_PATH = "/cache";

	private static final String SDCARD_PART_SIZE = "sdcard_storage";
	private Preference mSDCardSize;
	private static final String SDCARD_STORAGE_PATH = "/sdcard";

	public static boolean updatePreferenceToSpecificActivityOrRemove(Context context,
			PreferenceGroup parentPreferenceGroup, String preferenceKey, int flags) {

		Preference preference = parentPreferenceGroup.findPreference(preferenceKey);
		if (preference == null) {
			return false;
		}

		Intent intent = preference.getIntent();
		if (intent != null) {
			PackageManager pm = context.getPackageManager();
			List<ResolveInfo> list = pm.queryIntentActivities(intent, 0);
			int listSize = list.size();
			for (int i = 0; i < listSize; i++) {
				ResolveInfo resolveInfo = list.get(i);
				if ((resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM)
						!= 0) {
					preference.setIntent(new Intent().setClassName(
							resolveInfo.activityInfo.packageName,
							resolveInfo.activityInfo.name));
					return true;
				}
			}
		}
		parentPreferenceGroup.removePreference(preference);
		return true;
	}

	private String ObtainFSPartSize(String PartitionPath){
		String retstr;
		File extraPath = new File(PartitionPath);
		StatFs extraStat = new StatFs(extraPath.getPath());
		long eBlockSize = extraStat.getBlockSize();
		long eTotalBlocks = extraStat.getBlockCount();
		long eAvailableBlocks = extraStat.getAvailableBlocks();
		retstr = formatSize(eAvailableBlocks * eBlockSize);
		retstr += "  /  ";
		retstr += formatSize(eTotalBlocks * eBlockSize);
		return retstr;
	}

	private void SetupFSPartSize(){
		mSystemSize.setSummary(ObtainFSPartSize (SYSTEM_STORAGE_PATH));
		mDataSize.setSummary(ObtainFSPartSize (DATA_STORAGE_PATH));
		mCacheSize.setSummary(ObtainFSPartSize (CACHE_STORAGE_PATH));
		mSDCardSize.setSummary(ObtainFSPartSize (SDCARD_STORAGE_PATH));
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.goodies_settings);

		// Does

		findPreference(DOESGINGY_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && doesgingy", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(DOESGREEN_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && doesgreen", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(DOESLIGHTBLUE_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && doeslightblue", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(DOESMEDBLUE_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && doesmedblue", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(DOESORANGE_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && doesorange", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(DOESPINK_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && doespink", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(DOESPURPLE_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && doespurple", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(DOESRED_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && doesred", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(DOESSMOKED_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && doessmoked", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(DOESTWOBLUE_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && doestwoblue", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(DOESWHITE_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && doeswhite", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(DOESYELLOW_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && doesyellow", "Install bootanimation now?");
						return true;
					}
				}
		);

		// Eyes

		findPreference(EYESBLUEGREEN_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && eyesbluegreen", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(EYESCYAN_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && eyescyan", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(EYESGREEN_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && eyesgreen", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(EYESLIGHTBLUE_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && eyeslightblue", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(EYESORANGE_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && eyesorange", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(EYESPINK_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && eyespink", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(EYESPURPLE_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && eyespurple", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(EYESRED_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && eyesred", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(EYESYELLOW_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && eyesyellow", "Install bootanimation now?");
						return true;
					}
				}
		);

		// Gingy

		findPreference(GINGYFASTER_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && gingyfaster", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(GINGYSLOWER_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && gingyslower", "Install bootanimation now?");
						return true;
					}
				}
		);

		// Halloween

		findPreference(HALLOWSCARY_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && hallowscary", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(HALLOWSKULL_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && hallowskull", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(HALLOWSPIRAL_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && hallowspiral", "Install bootanimation now?");
						return true;
					}
				}
		);

		// Liquid

		findPreference(LIQUIDEARTH_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && liquidearth", "Install bootanimation now?");
						return true;
					}
				}
		);
		
		findPreference(LIQUIDDEFAULT_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && liquiddefault", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(LIQUIDROTATE_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && liquidrotate", "Install bootanimation now?");
						return true;
					}
				}
		);
		
		findPreference(LIQUIDSMOOTH_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && liquidsmooth", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(LIQUIDSPARKLE_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && liquidsparkle", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(LIQUIDSPLASH_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && liquidsplash", "Install bootanimation now?");
						return true;
					}
				}
		);

		// Other

		findPreference(ANDROIDFROG_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && androidfrog", "Install bootanimation now?");
						return true;
					}
				}
		);
		
	    findPreference(ANGRYBIRDS_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && angrybirds", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(APPLEOUCH_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && appleouch", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(ARCREACTOR_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && arcreactor", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(COLORTBOLT_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && colortbolt", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(CUPOFWATER_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && cupofwater", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(DARKBLUELFY_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && darkbluelfy", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(INMEMORYOF_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && inmemoryof", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(MERRYXMAS_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && merryxmas", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(ROSEBLOOM_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && rosebloom", "Install bootanimation now?");
						return true;
					}
				}
		);
		
		findPreference(SIMPLYSTUNNING_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && simplystunning", "Install bootanimation now?");
						return true;
					}
				}
		);
		
		findPreference(SOLARSYSTEM_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && solarsystem", "Install bootanimation now?");
						return true;
					}
				}
		);

		// Radial

		findPreference(RADIALDARKBLUE_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && radialdarkblue", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(RADIALDEFAULT_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && radialdefault", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(RADIALGREEN_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && radialgreen", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(RADIALLIGHTBLUE_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && radiallightblue", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(RADIALLIMEGREEN_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && radiallimegreen", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(RADIALORANGE_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && radialorange", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(RADIALPINK_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && radialpink", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(RADIALPURPLE_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && radialpurple", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(RADIALRED_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && radialred", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(RADIALSILVER_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && radialsilver", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(RADIALWHITE_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && radialwhite", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(RADIALYELLOW_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && radialyellow", "Install bootanimation now?");
						return true;
					}
				}
		);

		// Sexy

		findPreference(SEXYBOOBY_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && sexybooby", "Install bootanimation now?");
						return true;
					}
				}
		);

		findPreference(SEXYBOOTY_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && sexybooty", "Install bootanimation now?");
						return true;
					}
				}
		);

		// Switch

		findPreference(STOCKCAMERA_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && stcamera", "Install included camera app?");
						return true;
					}
				}
		);

		findPreference(OTHERCAMERA_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && thcamera", "Install alternate camera app?");
						return true;
					}
				}
		);

		findPreference(STOCKCONTACTS_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && stcontacts", "Install stock contacts app?");
						return true;
					}
				}
		);

		findPreference(THEMECONTACTS_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && thcontacts", "Install themed contacts app?");
						return true;
					}
				}
		);

		findPreference(STOCKGMAIL_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && stgmail", "Install stock gmail app?");
						return true;
					}
				}
		);

		findPreference(THEMEGMAIL_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && thgmail", "Install themed gmail app?");
						return true;
					}
				}
		);

		findPreference(ADWLAUNCHER_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && adwlauncher", "Install latest adw launcher?");
						return true;
					}
				}
		);

		findPreference(GOLAUNCHER_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && golauncher", "Install latest go launcher?");
						return true;
					}
				}
		);
		
		findPreference(LAUNCHERPRO_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && launcherpro", "Install latest launcher pro?");
						return true;
					}
				}
		);

		findPreference(STOCKLAUNCHER_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && stlauncher", "Install default stock launcher?");
						return true;
					}
				}
		);

		findPreference(STOCKMESSAGING_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && stmms", "Install included messaging app?");
						return true;
					}
				}
		);

		findPreference(OTHERMESSAGING_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && thmms", "Install alternate messaging app?");
						return true;
					}
				}
		);

		findPreference(STOCKMUSIC_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && stmusic", "Install included music app?");
						return true;
					}
				}
		);

		findPreference(OTHERMUSIC_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && thmusic", "Install alternate music app?");
						return true;
					}
				}
		);

		findPreference(STOCKSTATUSBAR_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && ststatus", "Install stock and quick reboot?");
						return true;
					}
				}
		);

		findPreference(THEMESTATUSBAR_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && thstatus", "Install themed and quick reboot?");
						return true;
					}
				}
		);

		// System

		findPreference(MOUNTRW_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && sysrw", "Mount system as read/write?");
						return true;
					}
				}
		);

		findPreference(MOUNTRO_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && sysro", "Mount system as read-only?");
						return true;
					}
				}
		);

		findPreference(ENABLEBOOT_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && bootani -e", "Enable the bootanimation?");
						return true;
					}
				}
		);

		findPreference(SOUNDOFF_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && camsound off", "Disables camera sounds?");
						return true;
					}
				}
		);

		findPreference(SOUNDON_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && camsound on", "Enable camera sounds?");
						return true;
					}
				}
		);

		findPreference(FIXPERMS_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && fixperms -v", "Fix system file permissions?");
						return true;
					}
				}
		);

		findPreference(CHUCKNORRIS_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && chucknorris", "Enable chuck norris is god mode?");
						return true;
					}
				}
		);

		findPreference(HOTREBOOT_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && hotreboot", "Perform a system quick hot reboot?");
						return true;
					}
				}
		);

		findPreference(CLEARCACHE_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && clearcache", "Wipe data/cache and quick reboot?");
						return true;
					}
				}
		);

		findPreference(CLEARDALVIK_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && cleardalvik", "Clear dalvikcache and quick reboot?");
						return true;
					}
				}
		);

		findPreference(CLEARSTATS_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && clearstats", "Wipe battery stats and quick reboot?");
						return true;
					}
				}
		);

		findPreference(DISABLEBOOT_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && bootani -d", "Disable the bootanimation?");
						return true;
					}
				}
		);

		findPreference(ZIPALIGN_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && zipalign_apks -a", "Optimize user apps and system apks?");
						return true;
					}
				}
		);

		findPreference(BLOCKADS_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && ads off", "Block ads in browser?");
						return true;
					}
				}
		);

		findPreference(SHOWADS_COMMAND).setOnPreferenceClickListener(
				new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						RunSuCommand("sleep 1 && ads on", "Show ads in browser?");
						return true;
					}
				}
		);

		mSystemSize = (Preference) findPreference(SYSTEM_PART_SIZE);
		mDataSize = (Preference) findPreference(DATA_PART_SIZE);
		mCacheSize = (Preference) findPreference(CACHE_PART_SIZE);
		mSDCardSize = (Preference) findPreference(SDCARD_PART_SIZE);
        SetupFSPartSize();

		final PreferenceGroup parentPreference = getPreferenceScreen();
		updatePreferenceToSpecificActivityOrRemove(this, parentPreference, BATTERY_HISTORY, 0);
		updatePreferenceToSpecificActivityOrRemove(this, parentPreference, BATTERY_STATUS, 0);
		updatePreferenceToSpecificActivityOrRemove(this, parentPreference, USAGE_STATISTICS, 0);
	}

	private void RunSuCommand(final String CommandStr, String YesNoString){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(YesNoString)
		.setCancelable(false)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				new SuServer().execute(CommandStr);
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

			}
		});
		AlertDialog alertDialog = builder.create();
		builder.show();
	}

	private String formatSize(long size) {
		return Formatter.formatFileSize(this, size);
	}

	private class SuServer extends AsyncTask<String, String, Void> {
		private ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			pd = ProgressDialog.show(GoodiesActivity.this, "Working", "Running Command...", true, false);
		}

		@Override
		protected void onProgressUpdate(String... values) {
		}

		@Override
		protected void onPostExecute(Void result) {
			pd.dismiss();
		}

		@Override
		protected Void doInBackground(String... args) {
			final Process p;

			try {
				p = Runtime.getRuntime().exec("su -c sh");
				BufferedReader stdInput = new BufferedReader(
						new InputStreamReader(p.getInputStream()));
				BufferedReader stdError = new BufferedReader(
						new InputStreamReader(p.getErrorStream()));
				BufferedWriter stdOutput = new BufferedWriter(
						new OutputStreamWriter(p.getOutputStream()));
				stdOutput.write(args[0] + " && exit\n");
				stdOutput.flush();
				Thread t = new Thread() {
					public void run() {
						try {
							p.waitFor();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				};
				t.start();
				while (t.isAlive()) {
					String status = stdInput.readLine();
					if (status != null)
						publishProgress(status);
					Thread.sleep(20);
				}
				stdInput.close();
				stdError.close();
				stdOutput.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
