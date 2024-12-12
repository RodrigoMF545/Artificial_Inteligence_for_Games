/*
 * Copyright (c) 2009-2010, Sergey Karakovskiy and Julian Togelius
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Mario AI nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

 package ch.idsia.scenarios;

 import ch.idsia.benchmark.tasks.BasicTask;
 import ch.idsia.tools.MarioAIOptions;
 import ch.idsia.agents.controllers.BehaviorAgent; // Importa o seu agente personalizado
 
 /**
  * Classe principal para iniciar o jogo Super Mario com o agente BehaviorAgent.
  */
 public final class Main {
     public static void main(String[] args) {
         // Cria as opções para inicializar o jogo
         final MarioAIOptions marioAIOptions = new MarioAIOptions(args);
 
         // Define o agente que será utilizado, neste caso o BehaviorAgent
         marioAIOptions.setAgent(new BehaviorAgent());
 
         // Configura a tarefa básica para o jogo
         final BasicTask basicTask = new BasicTask(marioAIOptions);
 
         // Reseta as opções e prepara o ambiente para iniciar
         basicTask.setOptionsAndReset(marioAIOptions);
 
         // Executa uma única partida
         basicTask.doEpisodes(1, true, 1);
 
         // Finaliza o programa
         System.exit(0);
     }
 }
 