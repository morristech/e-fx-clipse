/*******************************************************************************
 * Copyright (c) 2012 BestSolution.at and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tom Schindl<tom.schindl@bestsolution.at> - initial API and implementation
 *******************************************************************************/
package at.bestsolution.efxclipse.runtime.workbench.renderers.fx;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindowElement;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.workbench.IResourceUtilities;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.ISaveHandler.Save;
import org.eclipse.emf.common.util.URI;
import org.osgi.framework.Bundle;

import at.bestsolution.efxclipse.runtime.di.InjectingFXMLLoader;
import at.bestsolution.efxclipse.runtime.dialogs.Dialog;
import at.bestsolution.efxclipse.runtime.dialogs.MessageDialog;
import at.bestsolution.efxclipse.runtime.dialogs.MessageDialog.QuestionCancelResult;
import at.bestsolution.efxclipse.runtime.panels.FillLayoutPane;
import at.bestsolution.efxclipse.runtime.services.theme.Theme;
import at.bestsolution.efxclipse.runtime.services.theme.ThemeManager;
import at.bestsolution.efxclipse.runtime.services.theme.ThemeManager.Registration;
import at.bestsolution.efxclipse.runtime.workbench.fx.key.KeyBindingDispatcher;
import at.bestsolution.efxclipse.runtime.workbench.renderers.base.BaseRenderer;
import at.bestsolution.efxclipse.runtime.workbench.renderers.base.BaseWindowRenderer;
import at.bestsolution.efxclipse.runtime.workbench.renderers.base.widget.WLayoutedWidget;
import at.bestsolution.efxclipse.runtime.workbench.renderers.base.widget.WWidget;
import at.bestsolution.efxclipse.runtime.workbench.renderers.base.widget.WWindow;
import at.bestsolution.efxclipse.runtime.workbench.renderers.fx.widget.WLayoutedWidgetImpl;

@SuppressWarnings("restriction")
public class DefWindowRenderer extends BaseWindowRenderer<Stage> {

	protected Save[] promptToSave(MWindow element, Collection<MPart> dirtyParts, WWindow<Stage> widget) {
		Save[] response = new Save[dirtyParts.size()];
		@SuppressWarnings("unchecked")
		IResourceUtilities<Image> resourceUtilities = getModelContext(element).get(IResourceUtilities.class);

		MultiMessageDialog d = new MultiMessageDialog((Stage) widget.getWidget(), dirtyParts, resourceUtilities);
		if (d.open() == Dialog.OK_BUTTON) {
			List<MPart> parts = d.getSelectedParts();
			Arrays.fill(response, Save.NO);
			for (MPart p : parts) {
				response[parts.indexOf(p)] = Save.YES;
			}
		} else {
			Arrays.fill(response, Save.CANCEL);
		}

		return response;
	}

	protected Save promptToSave(MWindow element, MPart dirtyPart, WWindow<Stage> widget) {
		QuestionCancelResult r = MessageDialog.openQuestionCancelDialog((Stage) widget.getWidget(), "Unsaved changes", "'" + dirtyPart.getLocalizedLabel() + "' has been modified. Save changes?");

		switch (r) {
		case CANCEL:
			return Save.CANCEL;
		case NO:
			return Save.NO;
		case YES:
			return Save.YES;
		}

		return Save.CANCEL;
	}

	@Override
	protected Class<? extends WWindow<Stage>> getWidgetClass(MWindow window) {
		return WWindowImpl.class;
	}

	public static class WWindowImpl extends WLayoutedWidgetImpl<Stage, BorderPane, MWindow> implements WWindow<Stage> {
		private boolean support3d;
		private BorderPane rootPane;
		private BorderPane trimPane;
		private FillLayoutPane contentPane;
		private KeyBindingDispatcher dispatcher;
		private BorderPane decoratorPane;
		private WindowResizeButton windowResizeButton;
		private Stage stage;

		@Inject
		@Optional
		ThemeManager themeManager;

		private Registration sceneRegistration;

		private String decorationFXML;
		
		private boolean fullscreen;

		@Inject
		IEclipseContext context;
		
		IEclipseContext modelContext;
		
		MWindow mWindow;

		boolean initDone;
		
		@Inject
		public WWindowImpl(@Named(BaseRenderer.CONTEXT_DOM_ELEMENT) MWindow mWindow, @Optional KeyBindingDispatcher dispatcher) {
			this.mWindow = mWindow;
			this.support3d = mWindow.getPersistedState().get("fx.scene.3d") != null && Boolean.parseBoolean(mWindow.getPersistedState().get("fx.scene.3d"));
			this.dispatcher = dispatcher;
			this.modelContext = mWindow.getContext();
			this.decorationFXML = mWindow.getPersistedState().get("fx.stage.decoration");
		}
		
		@PostConstruct
		protected void init() {
			this.initDone = true;
			super.init();
		}

		@Override
		protected void doCleanup() {
			super.doCleanup();
			sceneRegistration.dispose();
		}

		@Override
		protected Stage createWidget() {
			stage = new Stage();
			stage.setFullScreen(fullscreen);
			stage.fullScreenProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					mWindow.getPersistedState().put(BaseWindowRenderer.KEY_FULL_SCREEN, newValue.toString());
				}
			});
			
			if( dispatcher != null ) {
				stage.addEventFilter(KeyEvent.KEY_PRESSED, dispatcher.getKeyHandler());	
			}
			
			this.rootPane = new BorderPane() {
				@Override
				protected void layoutChildren() {
					super.layoutChildren();
					if (windowResizeButton != null) {
						windowResizeButton.autosize();
						
						windowResizeButton.setLayoutX(getWidth() - windowResizeButton.getLayoutBounds().getWidth());
						windowResizeButton.setLayoutY(getHeight() - windowResizeButton.getLayoutBounds().getHeight());
					}
				}
			};

			this.trimPane = new BorderPane();
			this.rootPane.setCenter(trimPane);
			this.contentPane = new FillLayoutPane();
			this.trimPane.setCenter(contentPane);

			if (decorationFXML != null) {
				windowResizeButton = new WindowResizeButton(stage, 50, 50);
				decoratorPane = new BorderPane();
				decoratorPane.setTop(createTopDecoration(stage));
				rootPane.setTop(decoratorPane);
			}

			// TODO Should we create the scene on show???
			Scene s;
			if (support3d && Platform.isSupported(ConditionalFeature.SCENE3D)) {
				s = new Scene(rootPane, -1, -1, true);
				s.setCamera(new PerspectiveCamera());

			} else {
				s = new Scene(rootPane);
			}

			s.focusOwnerProperty().addListener(new ChangeListener<Node>() {
				List<WWidget<?>> lastActivationTree = new ArrayList<WWidget<?>>();

				@Override
				public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
					if (newValue != null) {
						final List<WWidget<?>> activationTree = new ArrayList<WWidget<?>>();

						do {
							if (newValue.getUserData() instanceof WWidget<?>) {
								activationTree.add((WWidget<?>) newValue.getUserData());
							}
						} while ((newValue = newValue.getParent()) != null);

						if (!lastActivationTree.equals(activationTree)) {
							final List<WWidget<?>> oldTreeReversed = new ArrayList<WWidget<?>>(lastActivationTree);
							final List<WWidget<?>> newTreeReversed = new ArrayList<WWidget<?>>(activationTree);
							Collections.reverse(oldTreeReversed);
							Collections.reverse(newTreeReversed);
							Iterator<WWidget<?>> it = newTreeReversed.iterator();

							while (it.hasNext()) {
								if (!oldTreeReversed.isEmpty()) {
									if (oldTreeReversed.get(0) == it.next()) {
										oldTreeReversed.remove(0);
										it.remove();
									} else {
										break;
									}
								} else {
									break;
								}
							}

							Collections.reverse(oldTreeReversed);
							Collections.reverse(newTreeReversed);

							lastActivationTree = activationTree;

							// Delay the execution maybe there's an intermediate
							// state we are not interested in
							// http://javafx-jira.kenai.com/browse/RT-24069
							Platform.runLater(new Runnable() {

								@Override
								public void run() {
									if (lastActivationTree == activationTree) {
										for (WWidget<?> w : oldTreeReversed) {
											w.deactivate();
										}

										for (WWidget<?> w : newTreeReversed) {
											w.activate();
										}
									}
								}
							});
						}
					}
				}
			});

			if (themeManager != null) {
				Theme theme = themeManager.getCurrentTheme();
				if (theme != null) {
					List<String> sUrls = new ArrayList<String>();
					for (URL url : theme.getStylesheetURL()) {
						sUrls.add(url.toExternalForm());
					}

					s.getStylesheets().addAll(sUrls);
				}
				sceneRegistration = themeManager.registerScene(s);
			}

			if( windowResizeButton != null ) {
				rootPane.getChildren().add(windowResizeButton);
			}
			
			stage.setScene(s);

			modelContext.set(Stage.class, stage);
			modelContext.set(Scene.class, s);
			
			return stage;
		}

		private Node createTopDecoration(final Stage stage) {
			URI uri = URI.createURI(decorationFXML);

			if (uri != null) {
				stage.initStyle(StageStyle.UNDECORATED);

				Bundle b = org.eclipse.core.runtime.Platform.getBundle(uri.segment(1));
				if (b != null) {
					try {
						StringBuilder sb = new StringBuilder();
						for (int i = 2; i < uri.segmentCount(); i++) {
							if (sb.length() != 0) {
								sb.append("/");
							}
							sb.append(uri.segment(i));
						}
						return (Node) InjectingFXMLLoader.create(context, b, sb.toString()).load();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

			return null;
		}

		@Override
		protected void bindProperties(Stage widget) {
			super.bindProperties(widget);
			bindProperty(UIEvents.Window.X, widget.xProperty());
			bindProperty(UIEvents.Window.Y, widget.yProperty());
			bindProperty(UIEvents.Window.WIDTH, widget.widthProperty());
			bindProperty(UIEvents.Window.HEIGHT, widget.heightProperty());
		}

		@Override
		public void setMainMenu(WLayoutedWidget<MMenu> menuWidget) {
			if (decoratorPane == null) {
				this.rootPane.setTop((Node) menuWidget.getStaticLayoutNode());
			} else {
				decoratorPane.setBottom((Node) menuWidget.getStaticLayoutNode());
			}
		}

		@Override
		protected BorderPane getWidgetNode() {
			return rootPane;
		}

		@Inject
		public void setX(@Named(UIEvents.Window.X) int x) {
			getWidget().setX(x);
		}

		@Inject
		public void setY(@Named(UIEvents.Window.Y) int y) {
			getWidget().setY(y);
		}

		@Inject
		public void setWidth(@Named(UIEvents.Window.WIDTH) int w) {
			getWidget().setWidth(w);
		}

		@Inject
		public void setHeight(@Named(UIEvents.Window.HEIGHT) int h) {
			getWidget().setHeight(h);
		}
		
		@Inject
		public void setVisible(@Named(UIEvents.UIElement.VISIBLE) boolean visible) {
			// Skip the init injection because the renderer will take care of showing the stage
			if( ! initDone ) {
				return;
			}
			
			System.err.println("Hello world");
			
			if( visible ) {
				getWidget().show();
			} else {
				getWidget().hide();
			}
		}

		@Inject
		public void setPersistedDate(@Named(UIEvents.ApplicationElement.PERSISTEDSTATE + "_" + BaseWindowRenderer.KEY_FULL_SCREEN) @Optional String fullScreen) {
			if( fullScreen != null ) {
				this.fullscreen = Boolean.parseBoolean(fullScreen);
				if( stage != null ) {
					stage.setFullScreen(fullscreen);
				}
			} else {
				this.fullscreen = false;
			}
		}
		
		@Override
		public void addStyleClasses(List<String> classnames) {
			rootPane.getStyleClass().addAll(classnames);
		}

		@Override
		public void setStyleId(String id) {
			rootPane.setId(id);
		}

		@Override
		public void show() {
			getWidget().toFront();
			getWidget().show();
		}

		@Inject
		public void setTitle(@Named(ATTRIBUTE_localizedLabel) String title) {
			getWidget().setTitle(title);
		}

		@Override
		public void setBottomTrim(WLayoutedWidget<MTrimBar> trimBar) {
			trimPane.setBottom((Node) trimBar.getStaticLayoutNode());
		}

		@Override
		public void setLeftTrim(WLayoutedWidget<MTrimBar> trimBar) {
			trimPane.setLeft((Node) trimBar.getStaticLayoutNode());
		}

		@Override
		public void setRightTrim(WLayoutedWidget<MTrimBar> trimBar) {
			trimPane.setRight((Node) trimBar.getStaticLayoutNode());
		}

		@Override
		public void setTopTrim(WLayoutedWidget<MTrimBar> trimBar) {
			Node g = (Node) trimBar.getStaticLayoutNode();
			trimPane.setTop(g);
		}

		@Override
		public void addChild(WLayoutedWidget<MWindowElement> widget) {
			System.err.println("CALLED: " + contentPane + " => " + widget + " => " + widget.getStaticLayoutNode());
			contentPane.getChildren().add((Node) widget.getStaticLayoutNode());
		}
		
		@Override
		public void removeChild(WLayoutedWidget<MWindowElement> widget) {
			contentPane.getChildren().remove((Node) widget.getStaticLayoutNode());
		}
		
		@Override
		public void addChild(int idx, WLayoutedWidget<MWindowElement> widget) {
			contentPane.getChildren().add(idx, (Node) widget.getStaticLayoutNode());
		}
	}

	static class MultiMessageDialog extends Dialog {
		private Collection<MPart> parts;
		private TableView<Row> tabView;

		private final IResourceUtilities<Image> resourceUtilities;
		private List<MPart> selectedParts;

		public MultiMessageDialog(Window parent, Collection<MPart> parts, IResourceUtilities<Image> resourceUtilities) {
			super(parent, "Save Resources");
			this.parts = parts;
			this.resourceUtilities = resourceUtilities;
		}

		public List<MPart> getSelectedParts() {
			return selectedParts;
		}

		@Override
		protected void okPressed() {
			selectedParts = new ArrayList<MPart>();
			for (Row r : tabView.getItems()) {
				if (r.isSelected()) {
					selectedParts.add(r.element.get());
				}
			}
			super.okPressed();
		}

		@Override
		protected Node createDialogArea() {
			BorderPane p = new BorderPane();
			Label l = new Label("Save resources to save");
			p.setTop(l);

			tabView = new TableView<Row>();

			{
				TableColumn<Row, Boolean> column = new TableColumn<Row, Boolean>();
				column.setCellFactory(new Callback<TableColumn<Row, Boolean>, TableCell<Row, Boolean>>() {

					@Override
					public TableCell<Row, Boolean> call(final TableColumn<Row, Boolean> param) {
						final CheckBox checkBox = new CheckBox();
						final TableCell<Row, Boolean> cell = new TableCell<Row, Boolean>() {

							@Override
							protected void updateItem(Boolean item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null) {
									checkBox.setDisable(true);
									checkBox.setSelected(false);
									checkBox.setOnAction(null);
								} else {
									checkBox.setDisable(false);
									checkBox.setSelected(item);
									checkBox.setOnAction(new EventHandler<ActionEvent>() {

										@Override
										public void handle(ActionEvent event) {
											tabView.edit(0, param);
											commitEdit(checkBox.isSelected());
										}
									});
								}
							}
						};

						cell.setGraphic(checkBox);
						return cell;
					}
				});
				column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Row, Boolean>>() {

					@Override
					public void handle(CellEditEvent<Row, Boolean> event) {
						event.getRowValue().selected.set(event.getNewValue());
					}
				});
				column.setCellValueFactory(new PropertyValueFactory<Row, Boolean>("selected"));
				tabView.getColumns().add(column);
			}

			{
				TableColumn<Row, MPart> column = new TableColumn<Row, MPart>();
				column.setCellFactory(new Callback<TableColumn<Row, MPart>, TableCell<Row, MPart>>() {

					@Override
					public TableCell<Row, MPart> call(TableColumn<Row, MPart> param) {
						return new TableCell<DefWindowRenderer.Row, MPart>() {
							@Override
							protected void updateItem(MPart item, boolean empty) {
								super.updateItem(item, empty);
								if (item != null) {
									setText(item.getLocalizedLabel());
									String uri = item.getIconURI();
									if (uri != null) {
										setGraphic(new ImageView(resourceUtilities.imageDescriptorFromURI(URI.createURI(uri))));
									}
								}
							}
						};
					}
				});
				column.setCellValueFactory(new PropertyValueFactory<Row, MPart>("element"));
				tabView.getColumns().add(column);
			}
			tabView.setEditable(true);

			List<Row> list = new ArrayList<Row>();
			for (MPart m : parts) {
				list.add(new Row(m));
			}
			tabView.setItems(FXCollections.observableArrayList(list));
			p.setCenter(tabView);

			return p;
		}
	}

	static class Row {
		private BooleanProperty selected = new SimpleBooleanProperty(this, "selected", true);
		private ObjectProperty<MPart> element = new SimpleObjectProperty<MPart>(this, "element");

		public Row(MPart element) {
			this.element.set(element);
		}

		public boolean isSelected() {
			return selected.get();
		}

		public void setSelected(boolean value) {
			this.selected.set(value);
		}

		public BooleanProperty selectedProperty() {
			return this.selected;
		}

		public MPart getElement() {
			return element.get();
		}

		public void setElement(MPart value) {
			element.set(value);
		}

		public ObjectProperty<MPart> elementProperty() {
			return element;
		}
	}
	
	static class WindowResizeButton extends Region {
	    private double dragOffsetX, dragOffsetY;

	    public WindowResizeButton(final Stage stage, final double stageMinimumWidth, final double stageMinimumHeight) {
	        setId("window-resize-button");
	        setPrefSize(11,11);
	        setOnMousePressed(new EventHandler<MouseEvent>() {
	            @Override public void handle(MouseEvent e) {
	                dragOffsetX = (stage.getX() + stage.getWidth()) - e.getScreenX();
	                dragOffsetY = (stage.getY() + stage.getHeight()) - e.getScreenY();
	                e.consume();
	            }
	        });
	        setOnMouseDragged(new EventHandler<MouseEvent>() {
	            @Override public void handle(MouseEvent e) {
	                ObservableList<Screen> screens = Screen.getScreensForRectangle(stage.getX(), stage.getY(), 1, 1);
	                final Screen screen;
	                if(screens.size()>0) {
	                    screen = Screen.getScreensForRectangle(stage.getX(), stage.getY(), 1, 1).get(0);
	                }
	                else {
	                    screen = Screen.getScreensForRectangle(0,0,1,1).get(0);
	                }
	                Rectangle2D visualBounds = screen.getVisualBounds();             
	                double maxX = Math.min(visualBounds.getMaxX(), e.getScreenX() + dragOffsetX);
	                double maxY = Math.min(visualBounds.getMaxY(), e.getScreenY() - dragOffsetY);
	                stage.setWidth(Math.max(stageMinimumWidth, maxX - stage.getX()));
	                stage.setHeight(Math.max(stageMinimumHeight, maxY - stage.getY()));
	                e.consume();
	            }
	        });
	    }
	}
}
