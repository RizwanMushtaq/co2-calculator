package com.rizwanmushtaq.utils;

import picocli.CommandLine;

public class VersionProvider implements CommandLine.IVersionProvider {
  private final ApplicationPropertiesProvider applicationPropertiesProvider;

  public VersionProvider() {
    applicationPropertiesProvider = new ApplicationPropertiesProvider();
  }

  @Override
  public String[] getVersion() {
    return new String[]{applicationPropertiesProvider.getVersion()};
  }
}
