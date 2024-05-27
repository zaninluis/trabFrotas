package service;

import entities.Veiculo;

import java.util.ArrayList;
import java.util.List;

public class VeiculoService {
    private List<Veiculo> veiculosDB;

    public VeiculoService() {
        veiculosDB = new ArrayList<>();
    }

    public void save(Veiculo veiculo) {
        veiculosDB.add(veiculo);
    }

    public List<Veiculo> getVeiculosDB() {
        return veiculosDB;
    }

    public Veiculo buscarPorPlaca(String placa) {
        for (Veiculo veiculo : veiculosDB) {
            if (veiculo.getPlaca().equalsIgnoreCase(placa)) {
                return veiculo;
            }
        }
        return null;
    }

    public boolean removerPorPlaca(String placa) {
        Veiculo veiculo = buscarPorPlaca(placa);
        if (veiculo != null) {
            veiculosDB.remove(veiculo);
            return true;
        }
        return false;
    }
}
