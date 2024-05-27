import entities.Carro;
import entities.Moto;
import entities.Veiculo;
import service.VeiculoService;

import java.util.List;
import java.util.Scanner;

public class CadVeiculo {
    private static Scanner scan;
    private static VeiculoService veiculoService;

    public static void main(String[] args) {
        scan = new Scanner(System.in);
        veiculoService = new VeiculoService();
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("Sistema de Gerenciamento de Frotas");
            System.out.println("Escolha uma das opções:");
            System.out.println("1 - Cadastrar Novo Veículo");
            System.out.println("2 - Listar todos os Veículos");
            System.out.println("3 - Pesquisar Veículo por Placa");
            System.out.println("4 - Remover Veículo");
            System.out.println("0 - Sair");

            opcao = lerInteiro("Digite a opção desejada: ");

            switch (opcao) {
                case 1:
                    save();
                    break;
                case 2:
                    imprimirVeiculos();
                    break;
                case 3:
                    buscarPorPlaca();
                    break;
                case 4:
                    removerPorPlaca();
                    break;
                case 0:
                    System.out.println("Volte logo!");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }

    public static void save() {
        Veiculo veiculoAdd;

        System.out.println("Qual o tipo de veículo");
        System.out.println("1 - Carro");
        System.out.println("2 - Moto");
        int tipoVeiculo = lerInteiro("Digite o tipo de veículo: ");

        System.out.print("Digite a marca do veículo: ");
        String marca = scan.nextLine();
        System.out.print("Digite o modelo do veículo: ");
        String modelo = scan.nextLine();
        int ano = lerAnoValido();
        String placa = lerPlacaValida();

        try {
            validarCampos(marca, modelo, ano, placa);

            if (tipoVeiculo == 1) {
                int numeroPortas = lerNumeroPortasValido();
                veiculoAdd = new Carro(marca, modelo, ano, placa, numeroPortas);
            } else {
                int partidaEletrica = lerInteiro("A moto possui partida elétrica: 1-Sim, 2-Não: ");
                boolean partida = partidaEletrica == 1;
                veiculoAdd = new Moto(marca, modelo, ano, placa, partida);
            }

            veiculoService.save(veiculoAdd);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar veículo: " + e.getMessage());
        }
    }

    private static void validarCampos(String marca, String modelo, int ano, String placa) throws Exception {
        if (marca.isEmpty() || modelo.isEmpty() || placa.isEmpty()) {
            throw new Exception("Todos os campos devem ser preenchidos.");
        }
        if (ano < 1900) {
            throw new Exception("O ano do veículo deve ser maior ou igual a 1900.");
        }
        if (placa.length() != 6) {
            throw new Exception("A placa do veículo deve ter exatamente 6 caracteres.");
        }
    }

    private static int lerAnoValido() {
        while (true) {
            int ano = lerInteiro("Digite o ano do veículo: ");
            if (ano >= 1900) {
                return ano;
            } else {
                System.out.println("Ano inválido. O ano deve ser maior ou igual a 1900.");
            }
        }
    }

    private static String lerPlacaValida() {
        while (true) {
            System.out.print("Digite a placa do veículo: ");
            String placa = scan.nextLine();
            if (placa.length() == 6) {
                return placa;
            } else {
                System.out.println("Placa inválida. A placa deve ter exatamente 6 caracteres.");
            }
        }
    }

    private static int lerNumeroPortasValido() {
        while (true) {
            int numeroPortas = lerInteiro("Digite o número de portas: ");
            if (numeroPortas > 0) {
                return numeroPortas;
            } else {
                System.out.println("Número de portas inválido. O número de portas deve ser maior que 0.");
            }
        }
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
            }
        }
    }

    private static void imprimirVeiculos() {
        List<Veiculo> veiculos = veiculoService.getVeiculosDB();
        for (Veiculo veiculo : veiculos) {
            System.out.println(veiculo);
            System.out.println("Tempo de uso: " + veiculo.calcularTempoDeUso() + " ano(s)");
            System.out.println(veiculo.calcularImposto());
        }
    }

    private static void buscarPorPlaca() {
        System.out.print("Digite a placa do veículo que deseja buscar: ");
        String placa = scan.nextLine();
        Veiculo veiculo = veiculoService.buscarPorPlaca(placa);
        if (veiculo != null) {
            System.out.println("Veículo encontrado:");
            System.out.println(veiculo);
        } else {
            System.out.println("Veículo não encontrado.");
        }
    }

    private static void removerPorPlaca() {
        System.out.print("Digite a placa do veículo que deseja remover: ");
        String placa = scan.nextLine();
        boolean removido = veiculoService.removerPorPlaca(placa);
        if (removido) {
            System.out.println("Veículo removido com sucesso.");
        } else {
            System.out.println("Veículo não encontrado.");
        }
    }
}
2