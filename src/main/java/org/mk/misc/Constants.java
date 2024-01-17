package org.mk.misc;

import org.mk.helpers.AuthorizationHelper;

import javax.inject.Named;
import javax.inject.Singleton;

import java.nio.file.Paths;

import static org.mk.plugin.ImportResultsMojo.*;

@Named
@Singleton
public class Constants {

    public static final String XRAY_BASE_URL = "https://xray.cloud.getxray.app/api/v2";

}
