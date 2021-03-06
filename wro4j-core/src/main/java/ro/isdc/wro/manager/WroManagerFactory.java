/*
 * Copyright (c) 2008. All rights reserved.
 */
package ro.isdc.wro.manager;

import ro.isdc.wro.util.ObjectFactory;

/**
 * Factory used to create {@link WroManager} objects.
 *
 * @author Alex Objelean
 * @created Created on Oct 31, 2008
 */
public interface WroManagerFactory extends ObjectFactory<WroManager> {
  /**
   * Called by filter indicating that it is being taken out of service.
   */
  public void destroy();
}
