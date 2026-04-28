package JuegosSuin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class FJuegosSuin extends JFrame {

    //Creación de componentes del menú
    JMenuBar menubar = new JMenuBar();
    JMenu menujuegos = new JMenu("Juegos");
    JMenu menuayuda = new JMenu("❔");
    JMenuItem menuitemppt = new JMenuItem("Piedra Papel Tijera");
    JMenuItem menuitemfrio = new JMenuItem("Frio-Caliente");
    JMenuItem menuitemins = new JMenuItem("Instrucciones");
    JMenuItem menuitemayuda = new JMenuItem("Ayuda");
    JMenuItem menuitemsalir = new JMenuItem("Salir");
    JLabel numerosUsados = new JLabel("");

    //Creación de pestañas
    JTabbedPane pestanias = new JTabbedPane();
    JPanel panelppt = new JPanel();
    JPanel panelfrio = new JPanel();

    String[] opciones = {"piedra", "papel", "tijera"};
    int[] victorias = {0, 0, 0};


    //Creacion de los numeros a usar durante el juego
    Integer limiteMaxFrio = 20;
    Integer limiteMaxArdes = 10;
    Integer limite0 = 0;
    Integer limite100 = 100;
    //RECU: Añadido un numero final para que el aleatorio funcione correctamente
    Integer numeroDefinitivo = generarNumero();

    public FJuegosSuin(String title) {
        setTitle(title);
        setSize(800, 600);

        setJMenuBar(menubar);
        menujuegos.add(menuitemppt);
        menujuegos.add(menuitemfrio);
        menujuegos.add(menuitemsalir);
        menubar.add(menujuegos);
        menuayuda.add(menuitemins);
        menuayuda.add(menuitemayuda);
        menubar.add(menuayuda);

        getContentPane().add(pestanias);
        panelppt = crearPanelPPT();
        panelfrio = crearPanelFrio();
        pestanias.addTab("Piedra Papel Tijera", panelppt);
        pestanias.addTab("Frio Caliente", panelfrio);

        menuitemppt.addActionListener(e -> pestanias.setSelectedIndex(0));
        menuitemfrio.addActionListener(e -> pestanias.setSelectedIndex(1));
        menuitemsalir.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(null, "<html>¿Seguro que quiere salir?</br>Se perderán los ranking</html>", "Salir del programa", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {
                return;
            }
            System.exit(0);
        });
        menuitemayuda.addActionListener(e -> JOptionPane.showMessageDialog(null,
                "<html>Aplicación para usar pestañas y menus</br>(DDI 2025)</html>",
                "Acerca de",
                JOptionPane.INFORMATION_MESSAGE)
        );

        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((int) (0.5 * (screenSize.width - getWidth())), (int) (0.5 * (screenSize.height - getHeight())), (int) getWidth(), (int) getHeight());

    }

    //Código copiado entero
    public JPanel crearPanelPPT() {

        JPanel panel = new JPanel(new BorderLayout(10, 10)); //10 en horizontal y 10 en vertical
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JPanel panelJuego = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel lblResultado = new JLabel("<html>Elige una opción<br>para comenzar</html>", JLabel.CENTER);
        lblResultado.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        panelJuego.add(lblResultado, gbc);
        JButton btnPiedra = new JButton("piedra");
        JButton btnPapel = new JButton("papel");
        JButton btnTijera = new JButton("tijera️");
        darFormatoBotones(btnPiedra);
        darFormatoBotones(btnPapel);
        darFormatoBotones(btnTijera);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panelJuego.add(btnPiedra, gbc);
        gbc.gridx = 1;
        panelJuego.add(btnPapel, gbc);
        gbc.gridx = 2;
        panelJuego.add(btnTijera, gbc);

        JPanel panelRanking = new JPanel(new GridLayout(1, 3, 10, 10));
        panelRanking.setBorder(BorderFactory.createTitledBorder("Ranking de Victorias"));
        JLabel lblUsuario = new JLabel("Usuario: 0", JLabel.CENTER);
        JLabel lblMaquina = new JLabel("Máquina: 0", JLabel.CENTER);
        JLabel lblEmpates = new JLabel("Empates: 0", JLabel.CENTER);
        lblUsuario.setOpaque(true);
        lblMaquina.setOpaque(true);
        lblEmpates.setOpaque(true);
        lblUsuario.setBackground(new Color(200, 255, 200));
        lblMaquina.setBackground(new Color(255, 200, 200));
        lblEmpates.setBackground(new Color(255, 255, 200));
        panelRanking.add(lblUsuario);
        panelRanking.add(lblMaquina);
        panelRanking.add(lblEmpates);
        panelRanking.putClientProperty("lblUsuario", lblUsuario);
        panelRanking.putClientProperty("lblMaquina", lblMaquina);
        panelRanking.putClientProperty("lblEmpates", lblEmpates);
        panelRanking.setVisible(true);

        JPanel panelControl = new JPanel();
        JCheckBox checkMostrarRanking = new JCheckBox("Mostrar Ranking");
        checkMostrarRanking.setSelected(true);
        panelControl.add(checkMostrarRanking);

        panel.add(panelJuego, BorderLayout.NORTH);
        panel.add(panelControl, BorderLayout.CENTER);
        panel.add(panelRanking, BorderLayout.SOUTH);

        checkMostrarRanking.addActionListener(e -> {
            panelRanking.setVisible(checkMostrarRanking.isSelected());
        });

        ActionListener botonActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String eleccionUsuario = "";
                if (e.getSource() == btnPiedra) {
                    eleccionUsuario = "piedra";
                } else if (e.getSource() == btnPapel) {
                    eleccionUsuario = "papel";
                } else if (e.getSource() == btnTijera) {
                    eleccionUsuario = "tijera";
                }
                String eleccionMaquina = opciones[new Random().nextInt(3)];

                String resultado = determinarGanadorPPT(eleccionUsuario, eleccionMaquina, victorias);
                lblResultado.setText("<html>Tú: " + eleccionUsuario + " | Máquina: " + eleccionMaquina + " </br> " + resultado + "</html>");

                actualizarRankingPPT(panelRanking, victorias);
            }
        };

        btnPapel.addActionListener(botonActionListener);
        btnPiedra.addActionListener(botonActionListener);
        btnTijera.addActionListener(botonActionListener);

        return panel;
    }


    public JPanel crearPanelFrio() {

        //Creamos el panel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //Creamos los GridBagConstraints y empezamos a meter labels creadas antes
        JPanel panelJuego = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        //Ponemos insets y rellenamos
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel lblResultado = new JLabel("<html>Pulsa Iniciar</html>", JLabel.CENTER);
        lblResultado.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        //Introducimos label
        panelJuego.add(lblResultado, gbc);

        //Introducimos el textfield donde meteremos el número
        JTextField tfNumero = new JTextField();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        tfNumero.setEditable(false);
        panelJuego.add(tfNumero, gbc);

        //Creamos la etiqueta que luego usaremos para dar indicaciones al usuario
        JLabel lblRespuesta = new JLabel("<html></html>", JLabel.CENTER);
        lblRespuesta.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        panelJuego.add(lblRespuesta, gbc);

        //Y creamos el boton de introducir
        JButton btintroducir = new JButton("Introducir");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        btintroducir.setToolTipText("Introducir el usuario");
        btintroducir.setMnemonic('I');
        tfNumero.setHorizontalAlignment(SwingConstants.CENTER);
        panelJuego.add(btintroducir, gbc);
        //Creamos un array de intentos donde guardaremos el numero de intentos (el programa no permitia usar una variable
        // que no fuese final, así que tuve que improvisar)
        int[] intentos = new int[]{0};
        //Creamos un ArrayList con los numeros que usaremos (Lo prefiero sobre un array normal)
        ArrayList<Integer> numeros = new ArrayList<Integer>();
        //Le damos funcionalidad al botón
        btintroducir.addActionListener(e -> {
            //RECU: Arreglado también error de parseo de número
            int numero = 0;
            try {
                numero = Integer.valueOf(tfNumero.getText());
            } catch (NumberFormatException ex) {
                System.out.println("Error de parseo de numero");
            }
            if (numero == 0) {
                lblRespuesta.setText("El número no es válido");
                return;
            }
            //Añadimos un intento y el número a la lista
            intentos[0]++;
            numeros.add(numero);
            //Aquí esta la lógica que determina si el número esta cerca o no del ganador
            if (numero > limite100 || numero < limite0) {
                lblRespuesta.setText("<html>No cumple los márgenes, inténtalo otra vez</html>");
                numerosUsados.setText("");
            } else {
                if (numero == numeroDefinitivo) {
                    lblRespuesta.setText("<html>Has ganado</html>");
                    //RECU: Ahora sale al ganar, había olvidado el setVisible
                    numerosUsados.setText("<html>Intentos: " + intentos[0] + "<br> Números usados: " + numeros.toString() + "</html>");
                    numerosUsados.setVisible(true);
                    System.out.println(intentos[0]);
                    System.out.println(numeros.toString());
                } else {
                    if (numeroDefinitivo > numero) {
                        //Aquí me hice un cacao y me olvidé de que existía && así que tuve que hacer que restarlo diera
                        //mayor que X para determinar que funcionase
                        if ((numeroDefinitivo - numero) > limiteMaxFrio) {
                            lblRespuesta.setText("<html>Frio</html>");
                        } else if ((numeroDefinitivo - numero) > limiteMaxArdes) {
                            lblRespuesta.setText("<html>Caliente</html>");
                        } else {
                            lblRespuesta.setText("<html>Ardes</html>");
                        }
                        //Y este else está aquí solo para diferenciar los casos en los que el numero introducido sea mayor
                        //que el nuestro, para poder hacer dichas restas correctamente
                    } else {
                        if ((numero - numeroDefinitivo) > limiteMaxFrio) {
                            lblRespuesta.setText("<html>Frio</html>");
                        } else if ((numero - numeroDefinitivo) > limiteMaxArdes) {
                            lblRespuesta.setText("<html>Caliente</html>");
                        } else {
                            lblRespuesta.setText("<html>Ardes</html>");
                        }
                    }
                }
            }
        });

        //Creamos los botones que el usuario puede utilizar para interactuar con la aplicación
        JButton botonIniciar = new JButton("Iniciar Juego");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        //RECU: tooltiptext y mnemonic
        botonIniciar.setToolTipText("Iniciar el juego");
        botonIniciar.setMnemonic('I');
        panelJuego.add(botonIniciar, gbc);
        botonIniciar.addActionListener(e -> {
            lblResultado.setText("<html>Introduce un número del " + limite0 + " al " + limite100 + "</html>");
            tfNumero.setEditable(true);
        });

        JButton botonReiniciar = new JButton("Reiniciar Juego");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        //RECU: tooltiptext y mnemonic
        botonReiniciar.setToolTipText("Reiniciar el juego");
        botonReiniciar.setMnemonic('R');
        panelJuego.add(botonReiniciar, gbc);
        botonReiniciar.addActionListener(e -> {
            this.numeroDefinitivo = generarNumero();
            numeros.clear();
            intentos[0] = 0;
            lblRespuesta.setText("Has reiniciado el juego");
            numerosUsados.setText("");
        });
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        numerosUsados.setHorizontalAlignment(SwingConstants.RIGHT);
        panelJuego.add(numerosUsados, gbc);
        numerosUsados.setFont(new Font("Arial", Font.PLAIN, 10));
        numerosUsados.setVisible(false);

        JButton botonComprobarNumeros = new JButton("Comprobar numeros usados");
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        //RECU: tooltiptext y mnemonic
        botonComprobarNumeros.setToolTipText("Comprobar los números usados hasta ahora");
        botonComprobarNumeros.setMnemonic('C');
        panelJuego.add(botonComprobarNumeros, gbc);
        botonComprobarNumeros.addActionListener(e -> {
            numerosUsados.setVisible(true);
            numerosUsados.setText(numeros.toString());
            lblRespuesta.setText("");
        });

        //Aquí creamos el botón aceptar de los ajustes que luego irá debajo de ellos
        JButton btaceptar = new JButton("Aceptar");
        //Creamos el panel de ajustes de límites
        JPanel panelAjustesLimites = new JPanel(new GridLayout(1, 4, 10, 10));
        panelAjustesLimites.setBorder(BorderFactory.createTitledBorder("Ajustes Límites"));
        JLabel lblLimites = new JLabel("Ajustar límites:", JLabel.CENTER);
        JTextField tfMenor = new JTextField();
        JLabel lblMenorMayor = new JLabel("a", JLabel.CENTER);
        JTextField tfMayor = new JTextField();
        //RECU: tooltiptext y mnemonic
        btaceptar.setToolTipText("Aceptar los ajustes");
        btaceptar.setMnemonic('A');
        btaceptar.addActionListener(e -> {
            //RECU: Ajustado para que no se puedan meter letras
            try {
                limite0 = Integer.valueOf(tfMenor.getText());
                limite100 = Integer.valueOf(tfMayor.getText());
            } catch (NumberFormatException ex) {
                if (!(tfMenor.getText().equals("") || tfMayor.getText().equals(""))) {
                    lblRespuesta.setText("El número no es valido");
                }
            }
            lblResultado.setText("<html>Introduce un número del " + limite0 + " al " + limite100 + "</html>");
            //Checkeos de comprobación para la función de cambiar ajustes
            System.out.println("========================================");
            System.out.println("Límite max ardes: " + limiteMaxArdes);
            System.out.println("Límite max frio " + limiteMaxFrio);
            System.out.println("Límite mínimo número: " + limite0);
            System.out.println("Límite máximo número: " + limite100);
            System.out.println("========================================");
        });
        panelAjustesLimites.add(lblLimites);
        panelAjustesLimites.add(tfMenor);
        panelAjustesLimites.add(lblMenorMayor);
        panelAjustesLimites.add(tfMayor);

        //Y creamos el panel de ajustes de máximos
        JPanel panelAjustes = new JPanel(new GridLayout(2, 2, 10, 10));
        panelAjustes.setBorder(BorderFactory.createTitledBorder("Ajustes Márgenes"));
        JLabel lblFrio = new JLabel("Ajustar frio (+20):", JLabel.CENTER);
        JTextField tfFrio = new JTextField();
        JLabel lblArdes = new JLabel("Ajustar Ardes (-10):", JLabel.CENTER);
        JTextField tfArdes = new JTextField();
        btaceptar.addActionListener(e -> {
            try {
                limiteMaxFrio = Integer.valueOf(tfFrio.getText());
                limiteMaxArdes = Integer.valueOf(tfArdes.getText());
            } catch (NumberFormatException ex) {
                if (!(tfFrio.getText().equals("") || tfArdes.getText().equals(""))) {
                    lblRespuesta.setText("El número no es valido");
                }
            }
            //Checkeos de comprobación para la función de cambiar ajustes
            System.out.println("========================================");
            System.out.println("Límite max ardes: " + limiteMaxArdes);
            System.out.println("Límite max frio " + limiteMaxFrio);
            System.out.println("Límite mínimo número: " + limite0);
            System.out.println("Límite máximo número: " + limite100);
            System.out.println("========================================");
        });
        panelAjustes.add(lblFrio);
        panelAjustes.add(tfFrio);
        panelAjustes.add(lblArdes);
        panelAjustes.add(tfArdes);

        panelAjustes.setVisible(true);
        panelAjustesLimites.setVisible(false);

        //Aquí creamos el panel de control, cogido de el juego de PPT
        JPanel panelControl = new JPanel();
        JRadioButton rbAjustesFrioArdes = new JRadioButton("Ajustes Frio - Ardes");
        rbAjustesFrioArdes.setSelected(true);
        JRadioButton rbAjustesLimites = new JRadioButton("Ajustes Límites");
        rbAjustesLimites.setSelected(false);
        JRadioButton rbAjustesCaliente = new JRadioButton("Ajustes Caliente");
        rbAjustesCaliente.setSelected(false);
        panelControl.add(rbAjustesFrioArdes);
        panelControl.add(rbAjustesLimites);

        //Y aquí creamos un panel de paneles, un apaño para poder meter todo en el mismo sitio sin que quedase muy mal
        //RECU: Ajustado para que no quede tan feo, he utilizado BorderLayout en ambos en lugar de grid
        JPanel panelPaneles = new JPanel(new BorderLayout());
        panelPaneles.add(panelAjustes, BorderLayout.NORTH);
        panelPaneles.add(panelAjustesLimites, BorderLayout.WEST);
        panelPaneles.add(btaceptar, BorderLayout.SOUTH);
        panel.add(panelJuego, BorderLayout.NORTH);
        panel.add(panelControl, BorderLayout.CENTER);
        panel.add(panelPaneles, BorderLayout.SOUTH);

        //Creamos la funcionalidad de los RadioButtons
        rbAjustesFrioArdes.addActionListener(e -> {
            panelAjustes.setVisible(rbAjustesFrioArdes.isSelected());
            rbAjustesLimites.setSelected(false);
            panelAjustesLimites.setVisible(false);
            rbAjustesCaliente.setSelected(false);
        });
        rbAjustesLimites.addActionListener(e -> {
            panelAjustesLimites.setVisible(rbAjustesLimites.isSelected());
            rbAjustesFrioArdes.setSelected(false);
            panelAjustes.setVisible(false);
            rbAjustesCaliente.setSelected(false);
        });
        //Devolvemos el panel
        return panel;
    }

    //Método copiado
    public JButton darFormatoBotones(JButton boton) {
        boton.setHorizontalAlignment(SwingConstants.CENTER);
        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        return boton;
    }

    //RECU: Ahora el número nuevo se genera y se imprime aquí
    public int generarNumero() {
        Random random = new Random();
        numeroDefinitivo = random.nextInt(limite0, limite100);
        System.out.println(numeroDefinitivo);
        return numeroDefinitivo;
    }

    //Método copiado
    private static String determinarGanadorPPT(String usuario, String maquina, int[] victorias) {
        if (usuario.equals(maquina)) {
            victorias[2]++;
            return "¡Empate!";
        }
        if ((usuario.equals("piedra") && maquina.equals("tijera"))
                || (usuario.equals("papel") && maquina.equals("piedra"))
                || (usuario.equals("tijera") && maquina.equals("papel"))) {
            int i = victorias[0]++;
            return "\n¡Ganaste!";
        } else {
            victorias[1]++;
            return "\n¡La máquina gana!";
        }
    }

    //Método copiado
    private static void actualizarRankingPPT(JPanel panelRanking, int[] victorias) {
        JLabel lblUsuario = (JLabel) panelRanking.getClientProperty("lblUsuario");
        JLabel lblMaquina = (JLabel) panelRanking.getClientProperty("lblMaquina");
        JLabel lblEmpates = (JLabel) panelRanking.getClientProperty("lblEmpates");
        lblUsuario.setText("Usuario: " + victorias[0]);
        lblMaquina.setText("Máquina: " + victorias[1]);
        lblEmpates.setText("Empates: " + victorias[2]);
    }
}
