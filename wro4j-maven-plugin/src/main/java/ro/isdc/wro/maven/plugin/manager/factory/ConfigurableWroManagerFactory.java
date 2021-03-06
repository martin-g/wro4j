/*
 * Copyright (C) 2011.
 * All rights reserved.
 */
package ro.isdc.wro.maven.plugin.manager.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import ro.isdc.wro.WroRuntimeException;
import ro.isdc.wro.extensions.manager.ExtensionsConfigurableWroManagerFactory;
import ro.isdc.wro.extensions.model.factory.SmartWroModelFactory;
import ro.isdc.wro.manager.factory.standalone.ConfigurableStandaloneContextAwareManagerFactory;
import ro.isdc.wro.manager.factory.standalone.StandaloneContext;
import ro.isdc.wro.maven.plugin.support.ExtraConfigFileAware;
import ro.isdc.wro.model.factory.WroModelFactory;
import ro.isdc.wro.model.resource.processor.ProcessorsUtils;
import ro.isdc.wro.model.resource.processor.ResourcePostProcessor;
import ro.isdc.wro.model.resource.processor.ResourcePreProcessor;

/**
 * Default implementaiton which use a property file to read the pre & post processors to be used during processing.
 *
 * @author Alex Objelean
 * @created 2 Aug 2011
 * @since 1.4.0
 */
public class ConfigurableWroManagerFactory
    extends ConfigurableStandaloneContextAwareManagerFactory implements ExtraConfigFileAware {
  private StandaloneContext standaloneContext;
  private File configProperties;
  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize(final StandaloneContext standaloneContext) {
    super.initialize(standaloneContext);
    this.standaloneContext = standaloneContext;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected WroModelFactory newModelFactory() {
    return SmartWroModelFactory.createFromStandaloneContext(standaloneContext);
  }

  /**
   * @return a map of preProcessors.
   */
  @Override
  protected Map<String, ResourcePreProcessor> createPreProcessorsMap() {
    final Map<String, ResourcePreProcessor> map = ProcessorsUtils.createPreProcessorsMap();
    ExtensionsConfigurableWroManagerFactory.pupulateMapWithExtensionsProcessors(map);
    return map;
  }

  /**
   * @return a map of postProcessors.
   */
  @Override
  protected Map<String, ResourcePostProcessor> createPostProcessorsMap() {
    final Map<String, ResourcePostProcessor> map = ProcessorsUtils.createPostProcessorsMap();
    ExtensionsConfigurableWroManagerFactory.pupulateMapWithExtensionsProcessors(map);
    return map;
  }
  /**
   * {@inheritDoc}
   */
  @Override
  protected Properties createProperties() {
    try {
      final Properties properties = new Properties();
      properties.load(new FileInputStream(configProperties));
      return properties;
    } catch (final IOException e) {
      throw new WroRuntimeException(
          "Exception while loading properties file from " + configProperties.getAbsolutePath(), e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void setExtraConfigFile(final File extraProperties) {
    this.configProperties = extraProperties;
  }
}
