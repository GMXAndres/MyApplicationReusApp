package com.andresgmx.myapplicationreusapp.db
import com.andresgmx.myapplicationreusapp.db.models.Reciclaje
import com.andresgmx.myapplicationreusapp.db.models.enums.*
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import java.util.Date


class ReporteFiltrado(
    val actividadesFiltradas: List<Reciclaje>,
    val pesoTotal: Double,
    val pesoPorMaterial: Map<TipoMaterial, Double>
)
class Reporte {
    companion object {
        fun filtrarActividadesReciclaje(
            reciclajes: List<Reciclaje>,
            fecha: LocalDate,
            periodicidad: Periodicidad
        ): ReporteFiltrado {
            val actividadesFiltradas = when (periodicidad) {
                Periodicidad.DIARIO -> filtrarActividadesDiarias(reciclajes, fecha)
                Periodicidad.SEMANAL -> filtrarActividadesSemanales(reciclajes, fecha)
                Periodicidad.MENSUAL -> filtrarActividadesMensuales(reciclajes, fecha)
                Periodicidad.ANUAL -> filtrarActividadesAnuales(reciclajes, fecha)
            }

            val pesoTotal = calcularPesoTotal(actividadesFiltradas)
            val pesoPorMaterial = calcularPesoPorMaterial(actividadesFiltradas)

            return ReporteFiltrado(actividadesFiltradas, pesoTotal, pesoPorMaterial)
        }

        private fun Date.toLocalDate(): LocalDate {
            return this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        }


        private fun filtrarActividadesDiarias(
            reciclajes: List<Reciclaje>,
            fecha: LocalDate
        ): List<Reciclaje> {
            return reciclajes.filter { it.fecha.toLocalDate() == fecha }
        }

        private fun filtrarActividadesSemanales(
            reciclajes: List<Reciclaje>,
            fecha: LocalDate
        ): List<Reciclaje> {
            val primerDiaDeLaSemana = fecha.with(TemporalAdjusters.previousOrSame(LocalDate.now().dayOfWeek))
            val ultimoDiaDeLaSemana = primerDiaDeLaSemana.plusDays(6)
            return reciclajes.filter { it.fecha.toLocalDate() in primerDiaDeLaSemana..ultimoDiaDeLaSemana }
        }

        private fun filtrarActividadesMensuales(
            reciclajes: List<Reciclaje>,
            fecha: LocalDate
        ): List<Reciclaje> {
            val primerDiaDelMes = fecha.with(TemporalAdjusters.firstDayOfMonth())
            val ultimoDiaDelMes = fecha.with(TemporalAdjusters.lastDayOfMonth())
            return reciclajes.filter { it.fecha.toLocalDate() in primerDiaDelMes..ultimoDiaDelMes }
        }

        private fun filtrarActividadesAnuales(
            reciclajes: List<Reciclaje>,
            fecha: LocalDate
        ): List<Reciclaje> {
            val primerDiaDelAnio = fecha.with(TemporalAdjusters.firstDayOfYear())
            val ultimoDiaDelAnio = fecha.with(TemporalAdjusters.lastDayOfYear())
            return reciclajes.filter { it.fecha.toLocalDate() in primerDiaDelAnio..ultimoDiaDelAnio }
        }

        private fun calcularPesoTotal(actividadesFiltradas: List<Reciclaje>): Double {
            var pesoTotal = 0.0
            for (actividad in actividadesFiltradas) {
                pesoTotal += actividad.peso
            }
            return pesoTotal
        }

        private fun calcularPesoPorMaterial(actividadesFiltradas: List<Reciclaje>): Map<TipoMaterial, Double> {
            val pesoPorMaterial = mutableMapOf<TipoMaterial, Double>()
            for (actividad in actividadesFiltradas) {
                val pesoExistente = pesoPorMaterial.getOrDefault(actividad.material, 0.0)
                pesoPorMaterial[actividad.material] = pesoExistente + actividad.peso
            }
            return pesoPorMaterial
        }
    }
}