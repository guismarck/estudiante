package app.estudiante.api;

//@AllArgsConstructor
//@RestController
public class EstudianteControler {

//    private final IEstudianteServicio estudianteServicio;
//    private final IUsuarioServicio usuarioServicio;
//    private final IDocenteServicio docenteServicio;

//    @Autowired
//    public EstudianteControler(IEstudianteServicio estudianteServicio, IUsuarioServicio usuarioServicio, IDocenteServicio docenteServicio) {
//        this.estudianteServicio = estudianteServicio;
//        this.usuarioServicio = usuarioServicio;
//        this.docenteServicio = docenteServicio;
//    }

  /*  @GetMapping(path = "/api/estudiantes")
    public ResponseEntity<List<Estudiante>> getAllEstudiantes(){
        Estudiante e = new Estudiante();
        e.setCod_estudiante("P0001");
        e.setEstado(true);
        e.setNombre_tutor("JUAN RAMON");
        e.setCedula("001-000000-000L");
        e.setCodigo_MINED("MINET_P0001");
        e.setDireccion("DEL PALO TE EMBROCO UNA CUADRA");
        e.setNombre_completo("ARMANDO RELAJO");
        e.setApellido_completo("BARRERA");
        e.setFecha_nacimiento(new java.sql.Date((new Date().getTime())));
        e.setPartidad_nacimiento("LA RAJA");
        e.setSexo("M");
        estudianteServicio.guardarEstudiante(e);
        List<Estudiante> list = estudianteServicio.ListarEstudiantes();
        estudianteServicio.eliminarEstudinate(e);
        return ResponseEntity.ok(list);
    }

    @GetMapping(path = "/api/usuarios")
    public ResponseEntity<List<Usuario>> getAllUsuarios(){
        Docente d = new Docente();
        d.setEstado(false);
        d.setCedula("001-000000-000L");
        d.setDireccion("DEL PALO TE EMBROCO UNA CUADRA");
        d.setNombre_completo("ARMANDO RELAJO");
        d.setApellido_completo("BARRERA");
        d.setFecha_nacimiento(new java.sql.Date((new Date().getTime())));
        d.setPartidad_nacimiento("LA RAJA");
        d.setSexo("M");

        docenteServicio.guardarDocente(d);

        Usuario e = new Usuario();
        e.setDocente(d);
        e.setEstado(false);
        e.setNombre("jdreyes");
        e.setContraseña("12345");
        e.setCorreo("jdreyes@gmail.com");
        e.setTipo_usuario("DEVELOPER");

//        Usuario e = new Usuario();
//        e.setEstado(false);
//        e.setCedula("001-000000-000L");
//        e.setDireccion("DEL PALO TE EMBROCO UNA CUADRA");
//        e.setNombre_completo("ARMANDO RELAJO");
//        e.setApellido_completo("BARRERA");
//        e.setFecha_nacimiento(new java.sql.Date((new Date().getTime())));
//        e.setPartidad_nacimiento("LA RAJA");
//        e.setSexo("M");
//        e.setNombre("jdreyes");
//        e.setContraseña("12345");
//        e.setCorreo("jdreyes@gmail.com");
//        e.setTipo_usuario("DEVELOPER");
        usuarioServicio.guardarUsuario(e);
        List<Usuario> list = usuarioServicio.ListarUsuario();
        //usuarioServicio.eliminarUsuario(e);
        return ResponseEntity.ok(list);
    }*/
}
