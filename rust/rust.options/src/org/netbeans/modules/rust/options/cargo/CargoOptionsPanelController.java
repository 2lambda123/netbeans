/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.rust.options.cargo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;

@OptionsPanelController.SubRegistration(
        location = "Rust",
        displayName = "#AdvancedOption_DisplayName_Cargo",
        keywords = "#AdvancedOption_Keywords_Cargo",
        keywordsCategory = "Rust/Cargo",
        position = 1000
)
@org.openide.util.NbBundle.Messages({"AdvancedOption_DisplayName_Cargo=Cargo", "AdvancedOption_Keywords_Cargo=rust cargo"})
public final class CargoOptionsPanelController extends OptionsPanelController {

    private CargoPanel panel;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private boolean changed;

    public void update() {
        getPanel().load();
        changed = false;
    }

    public void applyChanges() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                getPanel().store();
                changed = false;
            }
        });
    }

    public void cancel() {
        // need not do anything special, if no changes have been persisted yet
    }

    public boolean isValid() {
        return getPanel().valid();
    }

    public boolean isChanged() {
        return changed;
    }

    public HelpCtx getHelpCtx() {
        return null; // new HelpCtx("...ID") if you have a help set
    }

    public JComponent getComponent(Lookup masterLookup) {
        return getPanel();
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }

    private CargoPanel getPanel() {
        if (panel == null) {
            panel = new CargoPanel(this);
        }
        return panel;
    }

    void changed() {
        if (!changed) {
            changed = true;
            pcs.firePropertyChange(OptionsPanelController.PROP_CHANGED, false, true);
        }
        pcs.firePropertyChange(OptionsPanelController.PROP_VALID, null, null);
    }

}
